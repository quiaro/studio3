#!/bin/bash

# This install file will :
# - Install Grunt (if not installed)
# - Install Bower (if not installed)
# - Fetch all package (environment) dependencies 
# - Fetch all component (project) dependencies

# Check if node is installed
node --version 
if [ $? != 0 ]; then	
  echo "Abort install: Node is not installed or is currently not set in your PATH"; exit 1;
fi

# Check if npm is installed
npm -version
if [ $? != 0 ]; then
  echo "Abort install: NPM is not installed or is currently not set in your PATH"; exit 1;
fi

# Install Grunt CLI and Bower packages globally (if already installed, NPM will try to install them again)
npm install -g grunt-cli bower

# Fetch all package dependencies
npm install

# Fetch all component dependencies
bower install

echo "";
echo "Setup complete!";
exit 0;
