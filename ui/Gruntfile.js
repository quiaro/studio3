'use strict';

module.exports = function (grunt) {
  // load all grunt tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // configurable paths
  var yeomanConfig = {
    app: 'app',
    dist: 'target/META-INF/resources/studio-ui'
  };

  try {
    yeomanConfig.app = require('./bower.json').appPath || yeomanConfig.app;
  } catch (e) {}

  grunt.initConfig({
    yeoman: yeomanConfig,
    express: {
        options: {
            port: process.env.PORT || 9000
        },
        dev: {
            options: {
                script: './server/server.js'
            }
        },
        prod: {
            options: {
                script: './server/server.js'
            }
        }
    },
    watch: {
      coffee: {
        files: ['<%= yeoman.app %>/studio-ui/scripts/{,*/}*.coffee'],
        tasks: ['coffee:dist']
      },
      coffeeTest: {
        files: ['test/spec/{,*/}*.coffee'],
        tasks: ['coffee:test']
      },
      compass: {
        files: ['<%= yeoman.app %>/studio-ui/styles/{,*/}*.{scss,sass}'],
        tasks: ['compass']
      },
      express: {
        files: [
          '{.tmp,<%= yeoman.app %>}/index.html',
          '{.tmp,<%= yeoman.app %>}/studio-ui/i18n/*.json',
          '{.tmp,<%= yeoman.app %>}/studio-ui/styles/**/*.css',
          '{.tmp,<%= yeoman.app %>}/studio-ui/scripts/**/*.js',
          '{.tmp,<%= yeoman.app %>}/studio-ui/templates/**/*.html',
          '<%= yeoman.app %>/studio-ui/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}'
        ],
        tasks: ['express:dev'],
        options: {
            livereload: true,
            nospawn: true   //Without this option specified express won't be reloaded
        }
      }
    },

    open: {
      server: {
        url: 'http://localhost:<%= express.options.port %>'
      }
    },
    clean: {
      dist: {
        files: [{
          dot: true,
          src: [
            '.tmp',
            '<%= yeoman.dist %>/*'
          ]
        }]
      },
      server: '.tmp'
    },
    jshint: {
      options: {
        jshintrc: '.jshintrc'
      },
      all: [
        'Gruntfile.js',
        '<%= yeoman.app %>/studio-ui/scripts/**/*.js'
      ]
    },
    karma: {
      options: {
        configFile: 'karma.conf.js'
      },
      continuous: {
        browsers: ['Chrome']
      },
      dev: {
        autoWatch: true,
        singleRun: false
      }
    },
    coffee: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/studio-ui/scripts',
          src: '{,*/}*.coffee',
          dest: '.tmp/scripts',
          ext: '.js'
        }]
      },
      test: {
        files: [{
          expand: true,
          cwd: 'test/spec',
          src: '{,*/}*.coffee',
          dest: '.tmp/spec',
          ext: '.js'
        }]
      }
    },
    compass: {
      options: {
        sassDir: '<%= yeoman.app %>/studio-ui/styles',
        cssDir: '.tmp/styles',
        imagesDir: '<%= yeoman.app %>/studio-ui/images',
        javascriptsDir: '<%= yeoman.app %>/studio-ui/scripts',
        fontsDir: '<%= yeoman.app %>/studio-ui/fonts',
        importPath: '<%= yeoman.app %>/studio-ui/components',
        relativeAssets: true
      },
      dist: {},
      server: {
        options: {
          debugInfo: true
        }
      }
    },
    useminPrepare: {
      html: '<%= yeoman.app %>/index.html',
      options: {
        dest: '<%= yeoman.dist %>'
      }
    },
    usemin: {
      html: ['<%= yeoman.dist %>/**/*.html'],
      css: ['<%= yeoman.dist %>/studio-ui/styles/**/*.css'],
      options: {
        dirs: ['<%= yeoman.dist %>']
      }
    },
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/studio-ui/images',
          src: '**/*.{png,jpg,jpeg}',
          dest: '<%= yeoman.dist %>/studio-ui/images'
        }]
      }
    },
    cssmin: {
      dist: {
        files: {
          '<%= yeoman.dist %>/studio-ui/styles/studio.css': [
            '<%= yeoman.app %>/studio-ui/styles/*.css'
          ]}
      }
    },
    htmlmin: {
      dist: {
        options: {
          removeCommentsFromCDATA: true,
          // https://github.com/yeoman/grunt-usemin/issues/44
          // collapseWhitespace: true,
          collapseBooleanAttributes: true,
          removeAttributeQuotes: true,
          removeRedundantAttributes: true,
          useShortDoctype: true,
          // May want to declare Angular directives as empty attributes
          // removeEmptyAttributes: true,
          removeOptionalTags: true
        },
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>',
          src: ['studio-ui/templates/**/*.tpl.html'],
          dest: '<%= yeoman.dist %>'
        }]
      }
    },
    ngmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>/studio-ui/scripts',
          src: '*.js',
          dest: '<%= yeoman.dist %>/studio-ui/scripts'
        }]
      }
    },
    uglify: {
      dist: {
        files: {
          '<%= yeoman.dist %>/studio-ui/scripts/scripts.js': [
            '<%= yeoman.dist %>/studio-ui/scripts/scripts.js'
          ]
        }
      }
    },
    rev: {
      dist: {
        files: {
          src: [
            '<%= yeoman.dist %>/studio-ui/templates/**/*.tpl.html',
            '<%= yeoman.dist %>/studio-ui/scripts/**/*.js',
            '<%= yeoman.dist %>/studio-ui/styles/**/*.css',
            '<%= yeoman.dist %>/studio-ui/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
            '<%= yeoman.dist %>/studio-ui/fonts/*'
          ]
        }
      }
    },
    copy: {
      dist: {
        files: [{
          expand: true,
          dot: true,
          cwd: '<%= yeoman.app %>',
          dest: '<%= yeoman.dist %>',
          src: [
            '*.{ico,txt}',
            'studio-ui/i18n/*.json',
            'studio-ui/config/*.json',
            'studio-ui/lib/**/*.min.js',
            'studio-ui/lib/**/*.min.css',
            'studio-ui/images/**/*.{gif,webp,ico}',
            'studio-ui/styles/**/*.min.css',
            'studio-ui/styles/studio.css',
            'studio-ui/templates/**/*.tpl.html',

            // Special cases
            'studio-ui/lib/jquery/js/*.js',
            'studio-ui/lib/jquery/js/*.map',
            'studio-ui/lib/toastr/js/*.js',
            'studio-ui/lib/toastr/js/*.map',
            'studio-ui/lib/require*/**/*.js',
            'studio-ui/lib/bootstrap/fonts/*',

            // TODO: remove line below after creating space for plugins
            'studio-ui/scripts/dashboard/plugins/*.js'
          ]
        }]
      }
    },
    replace: {
      dev: {
        options: {
          variables: {
            'min': '',
            'includeTranslateErrorHandler': '<script ' +
              'src="studio-ui/lib/angular-translate-handler-log/js/angular-translate-handler-log.js"></script>',
            'livereload': '<script src="http://localhost:35729/livereload.js"></script>'
          }
        },
        files: [
          { src: ['<%= yeoman.app %>/index.html'],
            dest: '.tmp/index.html' }
        ]
      },
      build: {
        options: {
          variables: {
            'min': '.min',
            'includeTranslateErrorHandler': '',
            'livereload': ''
          }
        },
        files: [
          { src: ['<%= yeoman.app %>/index.html'],
            dest: '<%= yeoman.dist %>/index.html' }
        ]
      }
    },
    bower: {
      install: {
        options : {
          targetDir: './app/studio-ui/lib',
          layout: 'byComponent',
          verbose: true
        }
      }
    }
  });

  // Run unit tests on jasmine
  grunt.registerTask('test', [
    'clean:server',
    'coffee',
    // 'compass',
    // 'connect:test',
    'karma:dev'
  ]);

  // Run tests for code linting
  grunt.registerTask('lint', ['newer:jshint:all']);

  // Component update
  grunt.registerTask('cup', ['bower:install']);

  // Test look and feel locally
  grunt.registerTask('server', [
    'clean:server',
    'coffee:dist',
    // 'compass:server',
    'replace:dev',
    'express:dev',
    'open',
    'watch'
  ]);

  // Build the project for release
  grunt.registerTask('build', [
    'clean:dist',
    'jshint',
    'coffee',
    // 'compass:dist',
    // 'connect:test',
    'karma:continuous',
    'replace:build',
    'useminPrepare',
    'imagemin',
    'htmlmin',
    'concat',
    // 'cssmin',
    'copy',
    'ngmin',
    // 'uglify',
    'usemin'
  ]);

  grunt.registerTask('default', ['build']);
};
