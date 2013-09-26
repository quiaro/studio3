/* global define */
'use strict';

define({
  cmpIdAttr: 'data-studio-id',
  cmpTypeAttr: 'data-studio-type',
  cmpStatusAttr: 'data-studio-status',
  cmpParentAttr: 'data-studio-parent',
  cmpFocusClass: 'focus-component',

  cmpControlId: 'studio-component-controls',
  cmpControlBindAttr: 'data-studio-bound',
  cmpControlMsg: 'Click to Select',
  cmpControlButtons : [
    {
      content: 'Edit',
      classes: 'studio-edit'
    },
    {
      content: 'Move',
      classes: 'studio-move'
    },
    {
      content: 'Remove',
      classes: 'studio-remove'
    },
    {
      content: 'Select Parent',
      classes: 'studio-select-parent'
    }
  ],

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
  },

  REGISTRY: {
    path: '/config/registry.json',
    bridgedEventsKey: 'bridgedEvents'
  }

});