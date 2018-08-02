Downstream Builds Plugin
========================

This plugin is similar to the [Downstream build view plugin][dbvp],
but supports both Freestyle and Pipeline jobs (the Downstream
build view plugin does not support Pipeline as a consequence of
[a known limitation of Pipeline][29913]).

This plugin was written as an exercise in Java and Jenkins plugins
and in order to scratch a very particlar itch.  There is no guarantee
of support or continued development for this plugin.  Distribute and
install at your own risk.

Installation
------------

This plugin is not currently distributed via the Jenkins Plugin Center.
To build:

    mvn package

Then grab the .hpi file out of the target/ directory and upload it
to Jenkins.  More detailed installation info available [here][wiki].

[dbvp]: https://plugins.jenkins.io/downstream-buildview
[29913]: https://issues.jenkins-ci.org/browse/JENKINS-29913
[wiki]: https://wiki.jenkins.io/display/JENKINS/Plugin+tutorial#Plugintutorial-DistributingaPlugin
