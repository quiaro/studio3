'use strict';

module.exports = function (grunt) {
  // load all grunt tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // configurable paths
  var yeomanConfig = {
    app: 'client',
    dist: 'target/META-INF/resources'
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
      recess: {
        files: ['<%= yeoman.app %>/src/app/**/*.less'],
        tasks: ['recess:server']
      },
      express: {
        files: [
          '{.tmp,<%= yeoman.app %>}/index.html',
          '{.tmp,<%= yeoman.app %>}/src/app/**/*.less',
          '{.tmp,<%= yeoman.app %>}/src/app/**/*.js',
          '{.tmp,<%= yeoman.app %>}/src/app/**/*.html',
          '<%= yeoman.app %>/src/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}'
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
        '<%= yeoman.app %>/src/app/**/*.js'
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
    recess: {
        options: {
            compile: true
        },
        dist: {
            files: {
                '<%= yeoman.dist %>/src/styles/studio.css': [
                    '<%= yeoman.app %>/src/app/style.less'
                ],
                '<%= yeoman.dist %>/src/styles/editor.css': [
                    '<%= yeoman.app %>/src/app/editor/style.less'
                ]
            }
        },
        server: {
            files: {
                '.tmp/src/styles/studio.css': [
                    '<%= yeoman.app %>/src/app/style.less'
                ],
                '.tmp/src/styles/editor.css': [
                    '<%= yeoman.app %>/src/app/editor/style.less'
                ]
            }
        }
    },
    useminPrepare: {
        options: {
            dest: '<%= yeoman.dist %>'
        },
        html: '<%= yeoman.app %>/index.html'
    },
    usemin: {
        options: {
            dirs: ['<%= yeoman.dist %>']
        },
        html: ['<%= yeoman.dist %>/index.html',
               '<%= yeoman.dist %>/src/app/*/templates/*.html'],
        css: ['<%= yeoman.dist %>/src/styles/studio.css']
    },
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/src/images',
          src: '**/*.{png,jpg,jpeg}',
          dest: '<%= yeoman.dist %>/src/images'
        }]
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
          src: ['src/app/*/templates/**/*.html'],
          dest: '<%= yeoman.dist %>'
        }]
      }
    },
    ngmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>/src/app/**/*.js',
          src: '**/*.js',
          dest: '<%= yeoman.dist %>/src/app/**/*.js'
        }]
      }
    },
    uglify: {
      dist: {
        files: {
          '<%= yeoman.dist %>/src/scripts.js': [
            '<%= yeoman.dist %>/src/scripts.js'
          ]
        }
      }
    },
    rev: {
      dist: {
        files: {
          src: [
            '<%= yeoman.dist %>/src/app/**/*.tpl.html',
            '<%= yeoman.dist %>/src/app/**/*.js',
            '<%= yeoman.dist %>/src/app/**/*.css',
            '<%= yeoman.dist %>/src/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}'
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
            'lib/**/*.min.js',
            'lib/**/*.min.css',
            'src/images/**/*.{gif,webp,ico}',
            'src/styles/studio.css',
            'src/app/**/*.tpl.html',

            // Special cases
            'lib/jquery/js/*.js',
            'lib/jquery/js/*.map',
            'lib/toastr/js/*.js',
            'lib/toastr/js/*.map',
            'lib/require*/**/*.js',
            'lib/bootstrap/fonts/*'
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
              'src="lib/angular-translate-handler-log/js/angular-translate-handler-log.js"></script>',
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
          targetDir: './client/lib',
          layout: 'byComponent',
          verbose: true
        }
      }
    }
  });

  // Run unit tests on jasmine
  grunt.registerTask('test', [
    'clean:server',
    'karma:dev'
  ]);

  // Run tests for code linting
  grunt.registerTask('lint', ['newer:jshint:all']);

  // Component update
  grunt.registerTask('cup', ['bower:install']);

  // Test look and feel locally
  grunt.registerTask('server', [
    'clean:server',
    'replace:dev',
    'recess:server',
    'express:dev',
    'open',
    'watch'
  ]);

  // Build the project for release
  grunt.registerTask('build', [
    'clean:dist',
    'jshint',
    'recess:dist',
    'karma:continuous',
    'replace:build',
    'useminPrepare',
    'imagemin',
    'htmlmin',
    'concat',
    'copy',
    'ngmin',
    // 'uglify',
    'usemin'
  ]);

  grunt.registerTask('default', ['build']);
};
