/* global define, DEBUG */

define(function(require) {

    'use strict';

    // module dependencies
    var requestAgent = require('request_agent'),
        ServiceError = require('./serviceError');

    /*
     * @param object JS object
     * @param paramList Array of parameter objects -see validateParam for parameter format
     *        However, all these parameter objects are missing the 'value' property, which
     *        will be extracted from the first param (i.e. the JS object)
     *
     * @throw ServiceError if one of the params did not fulfill its requirements
     */
    function validateObject(object, paramList) {

        if (Array.isArray(paramList)) {

            paramList.forEach( function(param) {

                if (!param.id) {

                    throw new ServiceError({
                        type: 'MissingData',
                        message: 'param.id is required for all properties in the parameter list definition of an object'
                    });
                }

                if (param.id in object) {

                    param.value = object[param.id];
                    validateParam(param);

                } else {

                    throw new ServiceError({
                        type: 'MissingData',
                        message: 'Parameter definition claims missing property \'' + param.id + '\' exists in object'
                    });
                }

            });

        } else {

            throw new ServiceError({
                type: 'InvalidType',
                message: 'Incorrect value for paramList -expecting an array'
            });
        }
    }

    function checkRequired(param) {

        var value = param.value,
            required = param.required,
            empty = (typeof param.empty === 'boolean') ? param.empty : true;

        if (required) {

            if (typeof value === 'undefined' || value === null) {

                throw new ServiceError({
                    type: 'MissingField',
                    message: param.name + ' is required, but it is null or undefined'
                });

            } else if (!empty && typeof value === 'string' && !value) {

                throw new ServiceError({
                    type: 'MissingField',
                    message: param.name + ' is required, but it is an empty string'
                });

            }
        }
    }

    /*
     * @param param parameter object of the form:
     *   {
     *     id: name of the field or property (required for validating properties of an object)
     *     name: "Display name" (used to display to the user)
     *     value: paramValue
     *     type: "object" | "string" | "integer"
     *     required: boolean (false by default)
     *     empty: boolean (true by default -allow empty strings)
     *     properties: validation information for the parameter properties
     *                 -applies only to parameters of type "object"
     *   }
     *
     * @throw ServiceError if the param does not fulfill its requirements
     *        ServiceError.type can be one of the following:
     *        - MissingField
     *        - InvalidType
     *        - MissingData (should not apply to external users)
     */

     // TO-DO: Reduce cyclomatic complexity of this function
    function validateParam(param) {

        // Create shortcuts
        var value;

        if (param && requestAgent.isPlainObject(param)) {

            value = param.value;

            // Check required fields
            checkRequired(param);

            // Only check the type of fields that have a value
            if (typeof value !== 'undefined') {

                switch (param.type) {
                    case 'string':
                        if (typeof value !== 'string') {

                            throw new ServiceError({
                                type: 'InvalidType',
                                message: 'Incorrect value for ' + param.name + ' -expecting a string'
                            });
                        } break;

                    case 'number':
                        if (!requestAgent.isNumeric(value)) {

                            throw new ServiceError({
                                type: 'InvalidType',
                                message: 'Incorrect value for ' + param.name + ' -expecting a number'
                            });
                        } break;

                    case 'object':
                        if (!requestAgent.isPlainObject(value)) {

                            throw new ServiceError({
                                type: 'InvalidType',
                                message: 'Incorrect value for ' + param.name + ' -expecting an object'
                            });

                        } else {
                            if (param.properties) {
                                // Validate its properties only if there are rules defined for these
                                validateObject(value, param.properties);
                            }
                        } break;

                    case 'array':
                        if (!Array.isArray(value)) {

                            throw new ServiceError({
                                type: 'InvalidType',
                                message: 'Incorrect value for ' + param.name + ' -expecting an array'
                            });
                        } break;

                    case 'function':
                        if (!requestAgent.isFunction(value)) {

                            throw new ServiceError({
                                type: 'InvalidType',
                                message: 'Incorrect value for ' + param.name + ' -expecting a function'
                            });
                        } break;
                }
            }

        } else {

            throw new ServiceError({
                type: 'InvalidType',
                message: 'Incorrect value for param -expecting an object'
            });
        }
    }

    /*
     * Validate a list of parameters
     * @param paramList Array of parameter objects -see validateParam for parameter format
     *
     * @throw ServiceError if one of the params did not fulfill its requirements
     */
    function validateParams(paramList) {

        if (Array.isArray(paramList)) {

            if (DEBUG) {
                console.info('Validate the following parameter list definition: ');
                console.log(paramList);
            }

            paramList.forEach( function(param) {
                validateParam(param);
            });

        } else {

            throw new ServiceError({
                type: 'InvalidType',
                message: 'Incorrect value for paramList -expecting an array'
            });
        }
    }

    return {
        validateObject: validateObject,
        validateParam: validateParam,
        validateParams: validateParams
    };

});

