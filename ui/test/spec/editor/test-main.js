// For more information on configuration, go to:
// https://github.com/karma-runner/karma/pull/476/files

require.config({
	baseUrl: '/app/',
	paths: {
		'ckeditor' : '../lib/ckeditor/ckeditor',
		'config' : '../scripts/editor/modules/config',
		'editor-pubsub' : '../scripts/editor/modules/pubsub',
		'domReady' : '../lib/requirejs-domready/js/domReady',
		'jquery' : '../lib/jquery/js/jquery.min',
		'jquery-private' : '../scripts/editor/modules/jquery-private',
		'pubsub' : '../lib/pubsub-js/pubsub'
	},
	shim: {
		'ckeditor' : {
			exports: 'ckeditor'
		}
	},
	map: {
    '*': { 'jquery': 'jquery-private' },

    // 'jquery-private' wants the real jQuery module.
    // If this line was not here, there would
    // be an unresolvable cyclic dependency.
    'jquery-private': { 'jquery': 'jquery' }
  }
});