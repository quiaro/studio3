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
        files: ['<%= yeoman.app %>/src/modules/*/styles/*.less', '<%= yeoman.app %>/src/plugins/*/styles/*.css'],
        tasks: ['recess:server']
      },
      express: {
        files: [
          '{.tmp,<%= yeoman.app %>}/index.html',
          '{.tmp,<%= yeoman.app %>}/src/modules/*/styles/*.less',
          '{.tmp,<%= yeoman.app %>}/src/modules/**/*.js',
          '{.tmp,<%= yeoman.app %>}/src/modules/**/*.html',
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
        '<%= yeoman.app %>/src/modules/*/scripts/**/*.js',
        '<%= yeoman.app %>/src/plugins/*/scripts/**/*.js'
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
                    '<%= yeoman.app %>/src/style.less'
                ],
                '<%= yeoman.dist %>/src/styles/editor.css': [
                    '<%= yeoman.app %>/src/modules/editor/style.less'
                ]
            }
        },
        server: {
            files: {
                '.tmp/src/styles/studio.css': [
                    '<%= yeoman.app %>/src/style.less'
                ],
                '.tmp/src/styles/editor.css': [
                    '<%= yeoman.app %>/src/modules/editor/style.less'
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
               '<%= yeoman.dist %>/src/modules/*/templates/*.html',
               '<%= yeoman.dist %>/src/modules/*/plugins/*.html'],
        css: ['<%= yeoman.dist %>/src/styles/studio.css',
              '<%= yeoman.dist %>/src/plugins/*/styles/*.css']
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
          src: ['src/modules/*/templates/**/*.html'],
          dest: '<%= yeoman.dist %>'
        }]
      }
    },
    ngmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>/src/modules/*/scripts',
          src: '**/*.js',
          dest: '<%= yeoman.dist %>/src/modules/*/scripts'
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
            '<%= yeoman.dist %>/src/templates/**/*.tpl.html',
            '<%= yeoman.dist %>/src/scripts/**/*.js',
            '<%= yeoman.dist %>/src/styles/**/*.css',
            '<%= yeoman.dist %>/src/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
            '<%= yeoman.dist %>/src/fonts/*'
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
            'src/config/*.json',
            'src/lib/**/*.min.js',
            'src/lib/**/*.min.css',
            'src/images/**/*.{gif,webp,ico}',
            'src/styles/studio.css',
            'src/templates/**/*.tpl.html',

            // Special cases
            'src/lib/jquery/js/*.js',
            'src/lib/jquery/js/*.map',
            'src/lib/toastr/js/*.js',
            'src/lib/toastr/js/*.map',
            'src/lib/require*/**/*.js',
            'src/lib/bootstrap/fonts/*'
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
              'src="src/lib/angular-translate-handler-log/js/angular-translate-handler-log.js"></script>',
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
          targetDir: './client/src/lib',
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
    'recess:server',
    'replace:dev',
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
