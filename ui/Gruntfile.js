'use strict';

module.exports = function(grunt) {
    // load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // configurable paths
    var appConfig = {
        output: {
            dev: 'dev',
            build: 'build',
            dist: 'target/META-INF/resources'
        },
        root: './client',
        path: {
            app: '/studio-ui/src/app',
            modules: '/studio-ui/src/modules',
            plugins: '/studio-ui/src/plugins',
            images: '/studio-ui/images',
            lib: '/studio-ui/lib'
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
                '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
                '<%= sdo.root %><%= sdo.path.modules %>/**/*.js',
                '<%= sdo.root %><%= sdo.path.plugins %>/**/*.js',
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
                files: [{
                    src: '<%= sdo.root %><%= sdo.path.app %>/styles/app.less',
                    dest: '<%= sdo.output.dev %>/studio-ui/studio.css'
                }, {
                    expand: true, // Enable dynamic expansion
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>', // Src matches are relative to this path
                    src: ['**/*.less'], // Actual pattern(s) to match.
                    dest: '<%= sdo.output.dev %><%= sdo.path.modules %>', // Destination path prefix.
                    ext: '.css' // Dest filepaths will have this extension.
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: ['**/*.less'],
                    dest: '<%= sdo.output.dev %><%= sdo.path.plugins %>',
                    ext: '.css'
                }]
            },
            build: {
                files: [{
                    src: '<%= sdo.root %><%= sdo.path.app %>/styles/app.less',
                    dest: '<%= sdo.output.build %>/studio-ui/studio.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: ['/**/*.less'],
                    dest: '<%= sdo.output.build %><%= sdo.path.modules %>',
                    ext: '.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: ['/**/*.less'],
                    dest: '<%= sdo.output.build %><%= sdo.path.plugins %>',
                    ext: '.css'
                }]
            },
            dist: {
                files: [{
                    src: '<%= sdo.root %><%= sdo.path.app %>/styles/app.less',
                    dest: '<%= sdo.output.dist %>/studio-ui/studio.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: ['**/*.less'],
                    dest: '<%= sdo.output.dist %><%= sdo.path.modules %>',
                    ext: '.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: ['/**/*.less'],
                    dest: '<%= sdo.output.dist %><%= sdo.path.plugins %>',
                    ext: '.css'
                }]
            }
        },

        imagemin: {
            build: {
                files: [{
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.images %>',
                    src: '**/*.{png,jpg,jpeg}',
                    dest: '<%= sdo.output.build %><%= sdo.path.images %>'
                }]
            },
            dist: {
                files: [{
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.images %>',
                    src: '**/*.{png,jpg,jpeg}',
                    dest: '<%= sdo.output.dist %><%= sdo.path.images %>'
                }]
            }
        },

        // uglify: {
        //   build: {
        //         src: '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
        //         dest: '<%= sdo.output.build %>/studio-ui/studio.js'
        //     }
        //   },
        //   dist: {
        //         src: '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
        //         dest: '<%= sdo.output.dist %>/studio-ui/studio.js'
        //   }
        // },

        ngmin: {
            build: {
                files: [{
                    src: '<%= sdo.output.build %>/studio-ui/studio.js',
                    dest: '<%= sdo.output.build %>/studio-ui/studio.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.modules %>'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.plugins %>'
                }, ]
            },
            dist: {
                files: [{
                    src: '<%= sdo.output.dist %>/studio-ui/studio.js',
                    dest: '<%= sdo.output.dist %>/studio-ui/studio.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.dist %><%= sdo.path.modules %>'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.dist %><%= sdo.path.plugins %>'
                }, ]
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
                src: '<%= sdo.root %>/index.html',
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
                options: {
                    targetDir: '<%= sdo.root %><%= sdo.path.lib %>',
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
                src: '<%= sdo.root %>/index.html',
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
                    '<%= sdo.root %>/index.html',
                    '<%= sdo.root %><%= sdo.path.images %>/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
                    '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
                    '<%= sdo.root %><%= sdo.path.modules %>/**/*.js',
                    '<%= sdo.root %><%= sdo.path.plugins %>/**/*.js'
                ],
                tasks: [],
                options: {
                    livereload: true,
                    nospawn: true //Without this option specified express won't be reloaded
                }
            },
            recess: {
                files: [
                    '<%= sdo.root %><%= sdo.path.app %>/styles/app.less',
                    '<%= sdo.root %><%= sdo.path.modules %>/**/*.less',
                    '<%= sdo.root %><%= sdo.path.plugins %>/**/*.less'
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
        'Start a live-reloading dev webserver on localhost for development', ['clean:dev', 'symlink:dev', 'replace:dev', 'recess:dev', 'express:dev', 'open', 'watch']);

    grunt.registerTask('build',
        'Build the application for production and run it against a mock server on localhost', ['clean:build', 'lint', 'karma:continuous',
            'replace:build', 'recess:build', 'imagemin', 'copy', 'ngmin'
        ]);

    grunt.registerTask('dist',
        'Build the application for production so that it is ready to be integrated into a .war or .jar file.', ['clean:dist', 'lint', 'karma:continuous',
            'replace:dist', 'recess:dist', 'imagemin', 'copy', 'ngmin'
        ]);

    grunt.registerTask('test',
        'Run unit tests on jasmine', ['clean:dev', 'karma:dev']);

    grunt.registerTask('lint',
        'Run jshint on code' ['newer:jshint:app']);

    grunt.registerTask('default', ['dev']);
};
