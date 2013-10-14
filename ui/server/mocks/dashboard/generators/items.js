[
    '{{repeat(10, 15)}}',
    {
        id: '{{guid}}',
        existence: function(idx) {
            var choices = ['existing', 'new'];
            return choices[this.numeric(0, choices.length - 1)];
        },
        type: function(idx) {
            var choices = ['page', 'component'];
            return choices[this.numeric(0, choices.length - 1)];
        },
        status: function(idx) {
            var choices = ['scheduled', 'processing'];
            return choices[this.numeric(0, choices.length - 1)];
        },
        name: function (idx) {
            return this.lorem(this.numeric(1,5), 'words');
        },
        url: "#/preview",
        lastEdited: '{{date(YYYY-MM-ddThh:mm:ss Z)}}',
        lastAuthor: '{{firstName}} {{surname}}',
        lastPersonalEdit: '{{date(YYYY-MM-ddThh:mm:ss Z)}}'
    }
]
