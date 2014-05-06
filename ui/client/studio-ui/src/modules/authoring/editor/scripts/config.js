/* global define */
'use strict';

define({
    cmp: {
        idAttr: 'data-studio-id',
        typeAttr: 'data-studio-type',
        statusAttr: 'data-studio-status',
        parentAttr: 'data-studio-parent',
        focusClass: 'focus-component'
    },
    cmpControls: {
        id: 'studio-component-controls',
        bindAttr: 'data-bound-to',
        buttons: [{
            name: 'edit',
            text: 'Edit',
            class: 's2do-edit',
            iconClass: 'glyphicon glyphicon-pencil',
            events: [{
                on: 'click',
                publish: 'editor/element/edit',
                data: 'component'
            }]
        }, {
            name: 'move',
            text: 'Move',
            class: 's2do-move',
            iconClass: 'glyphicon glyphicon-move'
        }, {
            name: 'remove',
            text: 'Remove',
            class: 's2do-remove',
            iconClass: 'glyphicon glyphicon-remove'
        }, {
            name: 'parent',
            text: 'Select Parent',
            class: 's2do-parent',
            iconClass: 'glyphicon glyphicon-arrow-up'
        }]
    },

  cmpInlineStyles: {
    locked: {
      elements: [{
          selector: '',
          styles: {
            'background-color': 'red',
            'position': 'relative'
          }
        }, {
          selector: '.s2dio-state',
          styles: {
            'background-color': 'white',
            'border': '1px solid red',
            'width': '30px',
            'height': '30px',
            'position': 'absolute',
            'top': '-15px',
            'left': '-15px'
          }
        }
      ]
    },
    read: {
      elements: [{
          selector: '',
          styles: {
            'background-color': 'gray',
            'position': 'relative'
          }
        }, {
          selector: '.s2dio-state',
          styles: {
            'background-color': 'white',
            'border': '1px solid red',
            'width': '30px',
            'height': '30px',
            'position': 'absolute',
            'top': '-15px',
            'left': '-15px'
          }
        }
      ]
    },
    edit: {
      elements: [{
          selector: '',
          styles: {
            'background-color': 'yellow',
            'position': 'relative'
          }
        }, {
          selector: '.s2dio-state',
          styles: {
            'background-color': 'white',
            'border': '1px solid blue',
            'width': '30px',
            'height': '30px',
            'position': 'absolute',
            'top': '-15px',
            'left': '-15px'
          }
        }
      ]
    },
    idle: {
      elements: [{
          selector: '',
          styles: {}
        }
      ]
    }
  }

});
