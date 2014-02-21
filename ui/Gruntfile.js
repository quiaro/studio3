'use strict';

module.exports = function (grunt) {
  // load all grunt tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // configurable paths
  var appConfig = {
    output: {
        dev: 'dev',
        build: 'build',
        dist: 'target/META-INF/resources'
    },
    src: {
        root: './client',
        app: './client/studio-ui/src/app',
        images: './client/studio-ui/src/images',
        lib: './client/studio-ui/lib',
        modules: './server/app/modules',
        plugins: './server/app/plugins'
    }
  };

  grunt.initConfig({
    sdo: appConfig,
    express: {
        options: {
            port: process.env.PORT || 9000,
            debug: false
        },
        dev: {
            options: {
                script: './server/dev/server.js'
            }
        },
        build: {
            options: {
                script: './server/build/server.js'
            }
        }
    },

    open: {
      server: {
        url: 'http://localhost:<%= express.options.port %>'
      }
    },

    clean: {
      dev: '<%= sdo.output.dev %>',
      build: '<%= sdo.output.build %>',
      dist: '<%= sdo.output.dist %>'
    },

    jshint: {
        options: {
            jshintrc: '.jshintrc'
        },
        app: [
            'Gruntfile.js',
            '<%= sdo.src.app %>/**/*.js',
            '<%= sdo.src.modules %>/**/*.js',
            '<%= sdo.src.plugins %>/**/*.js',
        ]
    },

    karma: {
      options: {
        configFile: './test/config/karma.conf.js'
      },
      dev: {
        autoWatch: true,
        singleRun: false
      },
      continuous: {
        browsers: ['Chrome']
      }
    },

    recess: {
        options: {
            compile: true
        },
        dev: {
            files: [
                {
                    src: '<%= sdo.src.app %>/styles/app.less',
                    dest: '<%= sdo.output.dev %>/studio-ui/studio.css'
                },
                {
                  expand: true,                     // Enable dynamic expansion
                  cwd: '<%= sdo.src.modules %>',    // Src matches are relative to this path
                  src: ['**/*.less'],              // Actual pattern(s) to match.
                  dest: '<%= sdo.output.dev %>/modules/',   // Destination path prefix.
                  ext: '.css'                       // Dest filepaths will have this extension.
                },
                {
                  expand: true,
                  cwd: '<%= sdo.src.plugins %>',
                  src: ['**/*.less'],
                  dest: '<%= sdo.output.dev %>/plugins/',
                  ext: '.css'
                }
            ]
        },
        build: {
            files: [
                {
                    src: '<%= sdo.src.app %>/styles/app.less',
                    dest: '<%= sdo.output.build %>/studio-ui/studio.css'
                },
                {
                  expand: true,                     // Enable dynamic expansion
                  cwd: '<%= sdo.src.modules %>',    // Src matches are relative to this path
                  src: ['/**/*.less'],              // Actual pattern(s) to match.
                  dest: '<%= sdo.output.build %>/modules/',   // Destination path prefix.
                  ext: '.css',                      // Dest filepaths will have this extension.
                },
                {
                  expand: true,                     // Enable dynamic expansion
                  cwd: '<%= sdo.src.plugins %>',    // Src matches are relative to this path
                  src: ['/**/*.less'], // Actual pattern(s) to match.
                  dest: '<%= sdo.output.build %>/plugins/',   // Destination path prefix.
                  ext: '.css',                      // Dest filepaths will have this extension.
                }
            ]
        },
        dist: {
            files: [
                {
                    src: '<%= sdo.src.app %>/styles/app.less',
                    dest: '<%= sdo.output.dist %>/studio-ui/studio.css'
                },
                {
                  expand: true,                     // Enable dynamic expansion
                  cwd: '<%= sdo.src.modules %>',    // Src matches are relative to this path
                  src: ['**/*.less'],               // Actual pattern(s) to match.
                  dest: '<%= sdo.output.dist %>/modules/',   // Destination path prefix.
                  ext: '.css',                      // Dest filepaths will have this extension.
                },
                {
                  expand: true,                     // Enable dynamic expansion
                  cwd: '<%= sdo.src.plugins %>',    // Src matches are relative to this path
                  src: ['/**/*.less'], // Actual pattern(s) to match.
                  dest: '<%= sdo.output.dist %>/plugins/',   // Destination path prefix.
                  ext: '.css',                      // Dest filepaths will have this extension.
                }
            ]
        }
    },

    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= sdo.src.images %>',
          src: '**/*.{png,jpg,jpeg}',
          dest: '<%= sdo.output.dist %>/studio-ui/src/images'
        }]
      }
    },

    // uglify: {
    //   build: {
    //         src: '<%= sdo.src.app %>/**/*.js',
    //         dest: '<%= sdo.output.build %>/studio-ui/studio.js'
    //     }
    //   },
    //   dist: {
    //         src: '<%= sdo.src.app %>/**/*.js',
    //         dest: '<%= sdo.output.dist %>/studio-ui/studio.js'
    //   }
    // },

    ngmin: {
      build: {
        files: [
            {
                src: '<%= sdo.output.build %>/studio-ui/studio.js',
                dest: '<%= sdo.output.build %>/studio-ui/studio.js'
            },
            {
                expand: true,
                cwd: '<%= sdo.src.modules %>',
                src: '**/*.js',
                dest: '<%= sdo.output.build %>/modules'
            },
            {
                expand: true,
                cwd: '<%= sdo.src.plugins %>',
                src: '**/*.js',
                dest: '<%= sdo.output.build %>/plugins'
            },
        ]
      },
      dist: {
        files: [
            {
                src: '<%= sdo.output.dist %>/studio-ui/studio.js',
                dest: '<%= sdo.output.dist %>/studio-ui/studio.js'
            },
            {
                expand: true,
                cwd: '<%= sdo.src.modules %>',
                src: '**/*.js',
                dest: '<%= sdo.output.dist %>/modules'
            },
            {
                expand: true,
                cwd: '<%= sdo.src.plugins %>',
                src: '**/*.js',
                dest: '<%= sdo.output.dist %>/plugins'
            },
        ]
      }
    },

    // copy: {
    //     build: {

    //     },
    //     dist: {
    //         files: [{
    //           expand: true,
    //           dot: true,
    //           cwd: '<%= sdo.output.dev %>',
    //           dest: '<%= sdo.output.dist %>',
    //           src: [
    //             'studio-ui/lib/**/*.min.js',
    //             'studio-ui/lib/**/*.min.css',
    //             'studio-ui/src/images/**/*.{gif,webp,ico}',
    //             'studio-ui/src/styles/studio.css',
    //             'studio-ui/src/app/**/*.tpl.html',

    //             // Special cases
    //             'studio-ui/lib/jquery/js/*.js',
    //             'studio-ui/lib/jquery/js/*.map',
    //             'studio-ui/lib/toastr/js/*.js',
    //             'studio-ui/lib/toastr/js/*.map',
    //             'studio-ui/lib/require*/**/*.js',
    //             'studio-ui/lib/bootstrap/fonts/*'
    //           ]
    //         }]
    //     }
    // },

    replace: {
        options: {
            variables: {
                'min': '.min',
                'livereload': ''
            }
        },
        dev: {
            options: {
                variables: {
                    'min': '',
                    'livereload': '<script src="http://localhost:35729/livereload.js"></script>'
                }
            },
            src: '<%= sdo.src.root %>/index.html',
            dest: '<%= sdo.output.dev %>/index.html'
        },
        build: {
            src: '<%= replace.dev.src %>',
            dest: '<%= sdo.output.build %>/index.html'
        },
        dist: {
            src: '<%= replace.dev.src %>',
            dest: '<%= sdo.output.dist %>/index.html'
        }
    },

    bower: {
      install: {
        options : {
          targetDir: '<%= sdo.src.lib %>',
          layout: 'byComponent',
          verbose: true
        }
      }
    },

    symlink: {
        dev: {
          src: ['*', '!<%= sdo.output.dev %>'],
          dest: '<%= sdo.output.dev %>',
          expand: true,
          filter: 'isDirectory',
        }
    },

    useminPrepare: {
        build: {
            src: '<%= sdo.src.root %>/index.html',
            dest: '<%= sdo.output.build %>'
        },
        dist: {
            src: '<%= useminPrepare.build.src',
            dest: '<%= sdo.output.dist %>'
        }
    },

    usemin: {
        build: {
            html: ['<%= sdo.output.build %>/index.html'],
            js: ['<%= sdo.output.build %>/studio-ui/studio.js']
        },
        dist: {
            html: ['<%= sdo.src.dist %>/index.html'],
            js: ['<%= sdo.output.dist %>/studio-ui/studio.js']
        }
    },

    watch: {
      express: {
        files: [
          '<%= sdo.src.root %>/index.html',
          '<%= sdo.src.images %>/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
          '<%= sdo.src.app %>/**/*.js',
          '<%= sdo.src.modules %>/**/*.js',
          '<%= sdo.src.plugins %>/**/*.js'
        ],
        tasks: [],
        options: {
            livereload: true,
            nospawn: true   //Without this option specified express won't be reloaded
        }
      },
      recess: {
        files: [
            '<%= sdo.src.app %>/styles/app.less',
            '<%= sdo.src.modules %>/**/*.less',
            '<%= sdo.src.plugins %>/**/*.less'
        ],
        tasks: ['recess:dev']
      },
      replace: {
        files: ['<%= replace.dev.src %>'],
        tasks: ['replace:dev']
      }
    }
  });

  grunt.registerTask('dev',
    'Start a live-reloading dev webserver on localhost for development',
    ['clean:dev', 'symlink:dev', 'replace:dev', 'recess:dev', 'express:dev', 'open', 'watch']);

  grunt.registerTask('build',
    'Build the application for production and run it against a mock server on localhost',
    ['clean:build', 'lint', 'karma:continuous',
     'replace:build', 'recess:build', 'imagemin', 'copy', 'ngmin']);

  grunt.registerTask('dist',
    'Build the application for production so that it is ready to be integrated into a .war or .jar file.',
    ['clean:dist', 'lint', 'karma:continuous',
     'replace:dist', 'recess:dist', 'imagemin', 'copy', 'ngmin']);

  grunt.registerTask('test',
    'Run unit tests on jasmine',
    ['clean:dev', 'karma:dev']);

  grunt.registerTask('lint',
    'Run jshint on code'
    ['newer:jshint:app']);

  grunt.registerTask('default', ['dev']);
};
