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
            build: '/META-INF/resources',
            components: '/components'
        }
    };

    grunt.initConfig({
        sdo: appConfig,

        bower: {
            install: {
                options: {
                    targetDir: '<%= sdo.root %><%= sdo.path.lib %>',
                    layout: 'byComponent',
                    verbose: true
                }
            }
        },

        clean: {
            dev: '<%= sdo.output.dev %>',
            build: '<%= sdo.output.build %>',
            dist: '<%= clean.build %>',
            services: ['<%= sdo.root %><%= sdo.path.components %>/studio-js-services',
                       '<%= sdo.root %><%= sdo.path.lib %>/studio-js-services']
        },

        copy: {
            dev: {
                expand: true,
                cwd: '<%= sdo.root %><%= sdo.path.modules %>',
                src: '**/*.{html,css,js}',
                dest: '<%= sdo.output.dev %><%= sdo.path.modules %>'
            },
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
                    src: '**/*.{html,css,less}',
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

        open: {
            server: {
                url: 'http://localhost:<%= express.options.port %>'
            }
        },

        less: {
            options: {
                paths: ['<%= sdo.root %><%= sdo.path.app %>/styles',
                        '<%= sdo.root %><%= sdo.path.modules %>/common/styles'],
                ieCompat: false
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
                }]
            }
        },

        replace: {
            options: {
                variables: {
                    'min': '.min',
                    'livereload': '',
                    'debug': ''
                }
            },
            dev: {
                options: {
                    variables: {
                        'min': '',
                        'livereload': '<script src="http://localhost:35729/livereload.js"></script>',
                        'debug': 'var DEBUG = false;'
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

        symlink: {
            dev: {
                // There will not be a symbolic link for modules because
                // their less files need to be pre-compiled
                files: [{
                    src: '<%= sdo.root %><%= sdo.path.images %>',
                    dest: '<%= sdo.output.dev %><%= sdo.path.images %>'
                }, {
                    src: '<%= sdo.root %><%= sdo.path.lib %>',
                    dest: '<%= sdo.output.dev %><%= sdo.path.lib %>'
                }, {
                    src: '<%= sdo.root %><%= sdo.path.app %>',
                    dest: '<%= sdo.output.dev %><%= sdo.path.app %>'
                }, {
                    src: '<%= sdo.root %><%= sdo.path.plugins %>',
                    dest: '<%= sdo.output.dev %><%= sdo.path.plugins %>'
                }]
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
            less: {
                files: [
                    '<%= sdo.root %><%= sdo.path.app %>/styles/*.less',
                    '<%= sdo.root %><%= sdo.path.modules %>/**/*.less'
                ],
                tasks: ['less:dev']
            },
            replace: {
                files: ['<%= replace.dev.src %>'],
                tasks: ['replace:dev']
            },
            express: {
                files: [
                    '<%= sdo.output.dev %>/index.html',
                    '<%= sdo.output.dev %>/studio-ui/studio.css',
                    '<%= sdo.output.dev %><%= sdo.path.modules %>/**/*.css',
                    '<%= sdo.root %><%= sdo.path.images %>/**/*.{png,jpg,jpeg,gif,webp,svg,ico}',
                    '<%= sdo.root %><%= sdo.path.app %>/**/*.js',
                    '<%= sdo.root %><%= sdo.path.modules %>/**/*.{html,js,css,less}',
                    '<%= sdo.root %><%= sdo.path.plugins %>/**/*.{html,js,css,less}'
                ],
                tasks: ['any-newer:copy:dev'],
                options: {
                    livereload: true,
                    nospawn: true //Without this option specified express won't be reloaded
                }
            }
        }
    });

    grunt.registerTask('dev',
        'Start a live-reloading dev webserver on localhost for development',
        ['clean:dev', 'symlink:dev', 'replace:dev', 'less:dev', 'copy:dev', 'express:dev', 'open', 'watch']);

    grunt.registerTask('build',
        'Build the application for production and run it against a mock server on localhost',
        ['dist', 'express:build', 'open', 'watch:express']);

    grunt.registerTask('dist',
        'Build the application for production so that it is ready to be integrated into a .war or .jar file.',
        ['clean:build', 'lint',
            'replace:build', 'less:build', 'imagemin:build', 'copy:assets', 'buildjs', 'usemin']);

    grunt.registerTask('buildjs',
        'Minify and compress all javascript',
        ['useminPrepare', 'concat', 'copy:js', 'uglify:build']);

    grunt.registerTask('test',
        'Run unit tests on jasmine',
        ['clean:dev', 'karma:dev']);

    grunt.registerTask('lint',
        'Run jshint on code',
        ['newer:jshint:app']);

    grunt.registerTask('services',
        'Updates studio-js-services library',
        ['clean:services', 'bower:install']);

    grunt.registerTask('default', ['dev']);
};
