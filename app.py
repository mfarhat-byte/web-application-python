#!/usr/bin/env python
"""
mascot: a microservice for serving mascot data
"""
import json
from flask import Flask, jsonify, abort, make_response, render_template

APP = Flask(__name__)

# Load the data
with open('data.json', 'r', encoding="utf8") as data:
    MASCOTS = json.load(data)


# API route: return all mascots as JSON
@APP.route('/', methods=['GET'])
def get_mascots():
    """
    Function: get_mascots
    Input: none
    Returns: A list of mascot objects in JSON
    """
    return jsonify(MASCOTS)


# API route: return a single mascot by GUID
@APP.route('/<guid>', methods=['GET'])
def get_mascot(guid):
    """
    Function: get_mascot
    Input: a mascot GUID
    Returns: The mascot object with GUID matching the input
    """
    for mascot in MASCOTS:
        if guid == mascot['guid']:
            return jsonify(mascot)
    abort(404)
    return None


# Front-end route: render mascots in an HTML table
@APP.route('/view', methods=['GET'])
def view_mascots():
    """
    Renders mascots in a nicely formatted HTML table
    """
    return render_template('index.html', mascots=MASCOTS)


# Error handler for 404
@APP.errorhandler(404)
def not_found(error):
    """
    Function: not_found
    Input: The error
    Returns: HTTP 404 with JSON error message
    """
    return make_response(jsonify({'error': str(error)}), 404)


if __name__ == '__main__':
    APP.run("0.0.0.0", port=8080, debug=True)
