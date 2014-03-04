// Karma configuration

// base path, that will be used to resolve files and exclude
basePath = '../../client/studio-ui/';

// list of files / patterns to load in the browser
files = [
  JASMINE,
  JASMINE_ADAPTER,
  'lib/angular/js/angular.min.js',
  'lib/toastr/js/toastr.min.js',
  'lib/requirejs/js/require.js',
  'lib/angular-*/js/*.min.js',
  'lib/angular-mocks/js/angular-mocks.js',
  'lib/jquery/js/jquery.min.js',

  'src/app/**/*.js',
   // 'test/mock/**/*.js',
  '../../test/spec/**/*.js'
];

// list of files to exclude
exclude = [
	'lib/require-css/js/css-builder.js',
	'src/app/editor/main.js',
	'src/app/editor/scripts/*.js',
 ];

// test results reporter to use
// possible values: dots || progress || growl
reporters = ['progress'];

// web server port
port = 8082;

// cli runner port
runnerPort = 9100;

// enable / disable colors in the output (reporters and logs)
colors = true;

// level of logging
// possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
logLevel = LOG_INFO;

// enable / disable watching file and executing tests whenever any file changes
autoWatch = false;

// Start these browsers, currently available:
// - Chrome
// - ChromeCanary
// - Firefox
// - Opera
// - Safari (only Mac)
// - PhantomJS
// - IE (only Windows)
browsers = ['Chrome'];

// If browser does not capture in given timeout [ms], kill it
captureTimeout = 5000;

// Continuous Integration mode
// if true, it capture browsers, run tests and exit
singleRun = true;
