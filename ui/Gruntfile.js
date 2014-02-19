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
            port: process.env.PORT || 9000,
            debug: false
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
        files: ['<%= yeoman.app %>/studio-ui/src/app/**/*.less'],
        tasks: ['recess:server']
      },
      express: {
        files: [
          '{.tmp,<%= yeoman.app %>}/index.html',
          '{.tmp,<%= yeoman.app %>}/studio-ui/src/app/**/*.less',
          '{.tmp,<%= yeoman.app %>}/studio-ui/src/app/**/*.js',
          '{.tmp,<%= yeoman.app %>}/studio-ui/src/app/**/*.html',
          '<%= yeoman.app %>/studio-ui/src/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}'
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
        '<%= yeoman.app %>/studio-ui/src/app/**/*.js'
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
                '<%= yeoman.dist %>/studio-ui/src/styles/studio.css': [
                    '<%= yeoman.app %>/studio-ui/src/app/style.less'
                ],
                '<%= yeoman.dist %>/studio-ui/src/styles/editor.css': [
                    '<%= yeoman.app %>/studio-ui/src/app/editor/style.less'
                ]
            }
        },
        server: {
            files: {
                '.tmp/studio-ui/src/styles/studio.css': [
                    '<%= yeoman.app %>/studio-ui/src/app/style.less'
                ],
                '.tmp/studio-ui/src/styles/editor.css': [
                    '<%= yeoman.app %>/studio-ui/src/app/editor/style.less'
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
               '<%= yeoman.dist %>/studio-ui/src/app/**/*.tpl.html'],
        css: ['<%= yeoman.dist %>/studio-ui/src/styles/studio.css']
    },
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/studio-ui/src/images',
          src: '**/*.{png,jpg,jpeg}',
          dest: '<%= yeoman.dist %>/studio-ui/src/images'
        }]
      }
    },
    ngmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>/studio-ui/src/app/**/*.js',
          src: '**/*.js',
          dest: '<%= yeoman.dist %>/studio-ui/src/app/**/*.js'
        }]
      }
    },
    uglify: {
      dist: {
        files: {
          '<%= yeoman.dist %>/studio-ui/src/scripts.js': [
            '<%= yeoman.dist %>/studio-ui/src/scripts.js'
          ]
        }
      }
    },
    rev: {
      dist: {
        files: {
          src: [
            '<%= yeoman.dist %>/studio-ui/src/app/**/*.tpl.html',
            '<%= yeoman.dist %>/studio-ui/src/app/**/*.js',
            '<%= yeoman.dist %>/studio-ui/src/app/**/*.css',
            '<%= yeoman.dist %>/studio-ui/src/images/**/*.{png,jpg,jpeg,gif,webp,svg,ico}'
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
            'studio-ui/lib/**/*.min.js',
            'studio-ui/lib/**/*.min.css',
            'studio-ui/src/images/**/*.{gif,webp,ico}',
            'studio-ui/src/styles/studio.css',
            'studio-ui/src/app/**/*.tpl.html',

            // Special cases
            'studio-ui/lib/jquery/js/*.js',
            'studio-ui/lib/jquery/js/*.map',
            'studio-ui/lib/toastr/js/*.js',
            'studio-ui/lib/toastr/js/*.map',
            'studio-ui/lib/require*/**/*.js',
            'studio-ui/lib/bootstrap/fonts/*'
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
          targetDir: './client/studio-ui/lib',
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
    // 'recess:server',
    'express:dev',
    'open',
    'watch'
  ]);

  // Build the project for release
  grunt.registerTask('build', [
    'clean:dist',
    'jshint',
    // 'recess:dist',
    'karma:continuous',
    'replace:build',
    'useminPrepare',
    'imagemin',
    'concat',
    'copy',
    'ngmin',
    // 'uglify',
    'usemin'
  ]);

  grunt.registerTask('default', ['build']);
};
