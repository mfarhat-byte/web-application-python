FROM ubuntu:22.04

# Install Java + unzip + Z3 solver
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk unzip z3 && \
    rm -rf /var/lib/apt/lists/*

# Copy OpenJML zip file
COPY openjml-ubuntu-22.04-21-0.18.zip /opt/openjml.zip

# Extract it
RUN unzip /opt/openjml.zip -d /opt/openjml

# Add OpenJML to PATH
RUN ln -s /opt/openjml/openjml /usr/local/bin/openjml

# ----------------------------------------------------
# Fix: override broken bundled Z3 with system-installed Z3
# ----------------------------------------------------
RUN mkdir -p /opt/openjml/Solvers-linux && \
    ln -sf /usr/bin/z3 /opt/openjml/Solvers-linux/z3-4.3.1 && \
    ln -sf /usr/bin/z3 /opt/openjml/Solvers-linux/z3_4_3

# Optional: show installed solvers for debugging
RUN ls -l /opt/openjml/Solvers-linux

WORKDIR /src
