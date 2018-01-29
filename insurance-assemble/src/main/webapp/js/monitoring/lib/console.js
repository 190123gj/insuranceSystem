 /**
     * Protect window.console method calls, e.g. console is not defined on IE
     * unless dev tools are open, and IE doesn't define console.debug
     */
    (function() {
        var console = (window.console = window.console || {});
        var noop = function () {};
        var log = console.log || noop;
        var start = function(name) { return function(param) { log("Start " + name + ": " + param); } };
        var end = function(name) { return function(param) { log("End " + name + ": " + param); } };

        var methods = {
            // Internet Explorer (IE 10): http://msdn.microsoft.com/en-us/library/ie/hh772169(v=vs.85).aspx#methods
            // assert(test, message, optionalParams), clear(), count(countTitle), debug(message, optionalParams), dir(value, optionalParams), dirxml(value), error(message, optionalParams), group(groupTitle), groupCollapsed(groupTitle), groupEnd([groupTitle]), info(message, optionalParams), log(message, optionalParams), msIsIndependentlyComposed(oElementNode), profile(reportName), profileEnd(), time(timerName), timeEnd(timerName), trace(), warn(message, optionalParams)
            // "assert", "clear", "count", "debug", "dir", "dirxml", "error", "group", "groupCollapsed", "groupEnd", "info", "log", "msIsIndependentlyComposed", "profile", "profileEnd", "time", "timeEnd", "trace", "warn"

            // Safari (2012. 07. 23.): https://developer.apple.com/library/safari/#documentation/AppleApplications/Conceptual/Safari_Developer_Guide/DebuggingYourWebsite/DebuggingYourWebsite.html#//apple_ref/doc/uid/TP40007874-CH8-SW20
            // assert(expression, message-object), count([title]), debug([message-object]), dir(object), dirxml(node), error(message-object), group(message-object), groupEnd(), info(message-object), log(message-object), profile([title]), profileEnd([title]), time(name), markTimeline("string"), trace(), warn(message-object)
            // "assert", "count", "debug", "dir", "dirxml", "error", "group", "groupEnd", "info", "log", "profile", "profileEnd", "time", "markTimeline", "trace", "warn"

            // Firefox (2013. 05. 20.): https://developer.mozilla.org/en-US/docs/Web/API/console
            // debug(obj1 [, obj2, ..., objN]), debug(msg [, subst1, ..., substN]), dir(object), error(obj1 [, obj2, ..., objN]), error(msg [, subst1, ..., substN]), group(), groupCollapsed(), groupEnd(), info(obj1 [, obj2, ..., objN]), info(msg [, subst1, ..., substN]), log(obj1 [, obj2, ..., objN]), log(msg [, subst1, ..., substN]), time(timerName), timeEnd(timerName), trace(), warn(obj1 [, obj2, ..., objN]), warn(msg [, subst1, ..., substN])
            // "debug", "dir", "error", "group", "groupCollapsed", "groupEnd", "info", "log", "time", "timeEnd", "trace", "warn"

            // Chrome (2013. 01. 25.): https://developers.google.com/chrome-developer-tools/docs/console-api
            // assert(expression, object), clear(), count(label), debug(object [, object, ...]), dir(object), dirxml(object), error(object [, object, ...]), group(object[, object, ...]), groupCollapsed(object[, object, ...]), groupEnd(), info(object [, object, ...]), log(object [, object, ...]), profile([label]), profileEnd(), time(label), timeEnd(label), timeStamp([label]), trace(), warn(object [, object, ...])
            // "assert", "clear", "count", "debug", "dir", "dirxml", "error", "group", "groupCollapsed", "groupEnd", "info", "log", "profile", "profileEnd", "time", "timeEnd", "timeStamp", "trace", "warn"
            // Chrome (2012. 10. 04.): https://developers.google.com/web-toolkit/speedtracer/logging-api
            // markTimeline(String)
            // "markTimeline"

            assert: noop, clear: noop, trace: noop, count: noop, timeStamp: noop, msIsIndependentlyComposed: noop,
            debug: log, info: log, log: log, warn: log, error: log,
            dir: log, dirxml: log, markTimeline: log,
            group: start('group'), groupCollapsed: start('groupCollapsed'), groupEnd: end('group'),
            profile: start('profile'), profileEnd: end('profile'),
            time: start('time'), timeEnd: end('time')
        };

        for (var method in methods) {
            if ( methods.hasOwnProperty(method) && !(method in console) ) { // define undefined methods as best-effort methods
                console[method] = methods[method];
            }
        }
    })();