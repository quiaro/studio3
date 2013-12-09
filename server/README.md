# Studio Server

***

## Requirements

* MongoDB >= 2.4.8

## Installation

To build the Studio Server application:

1) Check out the Studio3 project:

    git clone https://github.com/craftercms/studio3.git

2) Build the Studio3 project:

    cd studio3
    mvn clean install

## Usage

To run Studio Server:

1) Start your mongo db

    cd your_mongo_install_directory/bin
    ./mongod

2) Go to the Studio Server sub-folder in the Studio3 project

    cd studio3/server

3) Run Studio Server on a Jetty Server

    mvn jetty:run
