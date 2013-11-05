[
    '{{repeat(8, 10)}}',
    {
        siteId: '{{guid}}',
        siteName: "mango",
        targetId: '{{guid}}'
        targetProperties: {
            name: function (idx) {
                return this.lorem(this.numeric(1,3), 'words');
            },
        },
        type: function (idx) {
            var choices = ['create', 'update', 'delete', 'publish', 'comment'];
            return choices[this.numeric(0, choices.length - 1)];
        },
        date: '{{date(YYYY-MM-ddThh:mm:ss Z)}}',
        creator: '{{firstName}} {{surname}}'
    }
]
