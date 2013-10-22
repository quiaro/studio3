// Karma configuration

// base path, that will be used to resolve files and exclude
basePath = '.';

// list of files / patterns to load in the browser
files = [
  JASMINE,
  JASMINE_ADAPTER,
  'app/studio-ui/lib/angular/js/angular.min.js',
  'app/studio-ui/lib/toastr/js/toastr.min.js',
  'app/studio-ui/lib/angular-translate/js/angular-translate.min.js',
  'app/studio-ui/lib/requirejs/js/require.js',
  'app/studio-ui/lib/angular-*/js/*.min.js',
  'app/studio-ui/lib/angular-mocks/js/angular-mocks.js',
  'app/studio-ui/lib/jquery/js/jquery.min.js',

  'app/studio-ui/scripts/**/*.js',
   // 'test/mock/**/*.js',
  'test/spec/**/*.js'
];

// list of files to exclude
exclude = [
	'app/studio-ui/lib/require-css/js/css-builder.js',
	'app/studio-ui/scripts/editor/main.js',
	'app/studio-ui/scripts/editor/modules/*.js',
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
