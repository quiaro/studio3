'use strict';

module.exports = function (grunt) {
  // load all grunt tasks
  require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

  // configurable paths
  var yeomanConfig = {
    app: 'app',
    dist: 'target/META-INF/resources'
  };

  try {
    yeomanConfig.app = require('./component.json').appPath || yeomanConfig.app;
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
        files: ['<%= yeoman.app %>/scripts/{,*/}*.coffee'],
        tasks: ['coffee:dist']
      },
      coffeeTest: {
        files: ['test/spec/{,*/}*.coffee'],
        tasks: ['coffee:test']
      },
      compass: {
        files: ['<%= yeoman.app %>/styles/{,*/}*.{scss,sass}'],
        tasks: ['compass']
      },
      express: {
        files: [
          '{.tmp,<%= yeoman.app %>}/index.html',
          '{.tmp,<%= yeoman.app %>}/i18n/*.json',
          '{.tmp,<%= yeoman.app %>}/styles/**/*.css',
          '{.tmp,<%= yeoman.app %>}/scripts/**/*.js',
          '{.tmp,<%= yeoman.app %>}/templates/**/*.html',
          '<%= yeoman.app %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}'
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
        '<%= yeoman.app %>/scripts/**/*.js'
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
          cwd: '<%= yeoman.app %>/scripts',
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
        sassDir: '<%= yeoman.app %>/styles',
        cssDir: '.tmp/styles',
        imagesDir: '<%= yeoman.app %>/images',
        javascriptsDir: '<%= yeoman.app %>/scripts',
        fontsDir: '<%= yeoman.app %>/styles/fonts',
        importPath: '<%= yeoman.app %>/components',
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
      css: ['<%= yeoman.dist %>/styles/**/*.css'],
      options: {
        dirs: ['<%= yeoman.dist %>']
      }
    },
    imagemin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.app %>/images',
          src: '{,*/}*.{png,jpg,jpeg}',
          dest: '<%= yeoman.dist %>/images'
        }]
      }
    },
    cssmin: {
      dist: {
        files: {
          '<%= yeoman.dist %>/styles/studio.css': [
            '<%= yeoman.app %>/styles/*.css'
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
          src: ['templates/**/*.tpl.html'],
          dest: '<%= yeoman.dist %>'
        }]
      }
    },
    ngmin: {
      dist: {
        files: [{
          expand: true,
          cwd: '<%= yeoman.dist %>/scripts',
          src: '*.js',
          dest: '<%= yeoman.dist %>/scripts'
        }]
      }
    },
    uglify: {
      dist: {
        files: {
          '<%= yeoman.dist %>/scripts/scripts.js': [
            '<%= yeoman.dist %>/scripts/scripts.js'
          ]
        }
      }
    },
    rev: {
      dist: {
        files: {
          src: [
            '<%= yeoman.dist %>/templates/**/*.tpl.html',
            '<%= yeoman.dist %>/scripts/**/*.js',
            '<%= yeoman.dist %>/styles/{,*/}*.css',
            '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp,svg}',
            '<%= yeoman.dist %>/styles/fonts/*'
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
            '.htaccess',
            'i18n/*.json',
            'lib/**/*.min.js',
            'lib/require*/**/*.js',
            'lib/**/*.min.css',
            'images/{,*/}*.{gif,webp}',
            'styles/fonts/*',
            'styles/**/fonts/*',
            'styles/**/*.min.css',
            'styles/studio.css',
            'templates/**/*.tpl.html'
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
          targetDir: './app/lib',
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
