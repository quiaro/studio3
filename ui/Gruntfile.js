'use strict';

module.exports = function(grunt) {
    // load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // configurable paths
    var appConfig = {
        output: {
            dev: 'dev',
            build: 'target'
        },
        root: './client',
        path: {
            app: '/studio-ui/src/app',
            modules: '/studio-ui/src/modules',
            plugins: '/studio-ui/src/plugins',
            images: '/studio-ui/images',
            lib: '/studio-ui/lib',
            build: '/META-INF/resources'
        }
    };

    grunt.initConfig({
        sdo: appConfig,
        express: {
            options: {
                port: process.env.PORT || 9000
            },
            dev: {
                options: {
                    script: './server/dev/server.js',
                    debug: false
                }
            },
            build: {
                options: {
                    script: './server/build/server.js',
                    node_env: 'production'
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
            build: '<%= sdo.output.build %>'
        },

        jshint: {
            options: {
                jshintrc: '.jshintrc'
            },
            app: [
                'Gruntfile.js',
                '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
                '<%= sdo.root %><%= sdo.path.modules %>/**/*.js',
                '<%= sdo.root %><%= sdo.path.plugins %>/**/*.js'
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
                    dest: '<%= sdo.output.build %><%= sdo.path.build %>/studio-ui/studio.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: ['**/*.less'],
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.modules %>',
                    ext: '.css'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: ['**/*.less'],
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.plugins %>',
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
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.images %>'
                }]
            }
        },

        copy: {
            assets: {
                files: [{
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.images %>',
                    src: '**/*.{gif,webp,ico}',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.images %>'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.lib %>',
                    src: '**/*',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.lib %>'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: '**/*.{html,css}',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.modules %>'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: '**/*.{html,css}',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.plugins %>'
                }]
            },
            js: {
                files: [{
                    src: '<%= sdo.output.build %><%= sdo.path.build %>/studio-ui/studio.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %>/studio-ui/studio.src.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.modules %>',
                    ext: '.src.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.root %><%= sdo.path.plugins %>',
                    src: '**/*.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.plugins %>',
                    ext: '.src.js'
                }]
            }
        },

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
                dest: '<%= sdo.output.build %><%= sdo.path.build %>/index.html'
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
                filter: 'isDirectory'
            }
        },

        uglify: {
            options: {
                preserveComments: false,
                report: 'min',
                sourceMap: true
            },
            build: {
                files: [{
                    src: '<%= sdo.output.build %><%= sdo.path.build %>/studio-ui/studio.src.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %>/studio-ui/studio.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.modules %>',
                    src: '**/*.src.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.modules %>',
                    ext: '.js'
                }, {
                    expand: true,
                    cwd: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.plugins %>',
                    src: '**/*.src.js',
                    dest: '<%= sdo.output.build %><%= sdo.path.build %><%= sdo.path.plugins %>',
                    ext: '.js'
                }]
            }
        },

        useminPrepare: {
            options: {
                dest: '<%= sdo.output.build %><%= sdo.path.build %>',
                flow: {
                    steps: {'js' : ['concat'] },
                    post: {}
                }
            },
            html: '<%= sdo.root %>/index.html'
        },

        usemin: {
            html: '<%= sdo.output.build %><%= sdo.path.build %>/index.html'
        },

        watch: {
            express: {
                files: [
                    '<%= sdo.root %><%= sdo.path.images %>/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
                    '<%= sdo.root %><%= sdo.path.app %>/**/*.{html,js,css}',
                    '<%= sdo.root %><%= sdo.path.modules %>/**/*.{html,js,css}',
                    '<%= sdo.root %><%= sdo.path.plugins %>/**/*.{html,js,css}'
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
        'Start a live-reloading dev webserver on localhost for development',
        ['clean:dev', 'symlink:dev', 'replace:dev', 'recess:dev', 'express:dev', 'open', 'watch']);

    grunt.registerTask('build',
        'Build the application for production and run it against a mock server on localhost',
        ['dist', 'express:build', 'open', 'watch']);

    grunt.registerTask('dist',
        'Build the application for production so that it is ready to be integrated into a .war or .jar file.',
        ['clean:build', 'lint',
            'replace:build', 'recess:build', 'imagemin:build', 'copy:assets', 'buildjs', 'usemin']);

    grunt.registerTask('buildjs',
        'Minify and compress all javascript',
        ['useminPrepare', 'concat', 'copy:js', 'uglify:build']);

    grunt.registerTask('test',
        'Run unit tests on jasmine',
        ['clean:dev', 'karma:dev']);

    grunt.registerTask('lint',
        'Run jshint on code',
        ['newer:jshint:app']);

    grunt.registerTask('default', ['dev']);
};
