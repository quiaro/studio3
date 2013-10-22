
===== REST Services Mocks & JSON generators =====

These folders should be a reflection of the backend controllers found in this URL:
https://github.com/craftercms/studio3/tree/master/server/src/main/java/org/craftercms/studio/controller/services/rest

This means that for each controller there should be a matching "controller folder".

>>> For example:
If a java controller exists with the name of auditController.java, then you should
find here a folder for this controller with the name of "audit".


Inside each controller folder you will find folders for each one of the REST services
that the controller exposes.

>>> For example:
If a java controller named auditController.java exposes two REST services:

1) /activity/{site}
2) /log/{site}

Then, you should find two folders within the "audit" folder: one named activity
and the other named log.


Inside each sub-folder, you'll find .json and .js files where each .json file is
an individual mocked response for said service.

>>> For example:
Inside the audit folder, there's a folder called activity. Within the activity folder
we might need to create 3 different mocked responses representing the following scenarios:

1) REST service call with no arguments
2) REST service call with argument A
3) REST service call with argument B

We can then create three different .json files, each one being the mocked response for each
one of these service calls:

1) noArguments.json
2) argumentA.json
3) argumentB.json


Along with the .json files, you should also find one or several .js files used to generate the
.json files. That means that if you need to make changes to a json file (because its
structure has changed, or you want to increase the number of records, etc) you can edit the
.js file with the same name to generate a new json file with the changes you need via
http://www.json-generator.com.

>>> For example:
You have a .json file named noArguments.json with the following information:

[
    {
        "id": "e38e8a96-c123-4633-8736-ca6525f7aa3b",
        "name": "minim",
        "lastAuthor": "Dave Grohl",
        "lastEdit": "1988-10-07T17:11:09 +06:00"
    },
    {
        "id": "b429d9ee-9e51-4f33-8197-606161e1dbdd",
        "name": "pariatur qui eu officia eiusmod",
        "lastAuthor": "Bruce Springsteen",
        "lastEdit": "2000-06-30T10:21:37 +06:00"
    }
]

You would like to add an extra field named "LastPersonalEdit" of type date and add more records
to this json. To do this, you can edit the .json file directly or change the .js file named generator.js that may currently look like this:

[
    '{{repeat(1, 2)}}',
    {
        id: '{{guid}}',
        name: function (idx) {
            return this.lorem(this.numeric(1,5), 'words');
        },
        lastAuthor: '{{firstName}} {{surname}}',
        lastEdit: '{{date(YYYY-MM-ddThh:mm:ss Z)}}'
    }
]

By changing generator.js to:

[
    '{{repeat(8, 10)}}',
    {
        id: '{{guid}}',
        name: function (idx) {
            return this.lorem(this.numeric(1,5), 'words');
        },
        lastAuthor: '{{firstName}} {{surname}}',
        lastEdit: '{{date(YYYY-MM-ddThh:mm:ss Z)}}',
        lastPersonalEdit: '{{date(YYYY-MM-ddThh:mm:ss Z)}}'
    }
]

You can go to http://www.json-generator.com and use this as input to generate a new
json object with the changes you need.


TODO: Find or create a Grunt taks that will automate the process of generating .json
files from the .js files.
