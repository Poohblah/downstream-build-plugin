package edu.colorado.oit.jenkins.plugins;

import java.util.Collection;
import java.util.Collections;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Run;
import hudson.model.View;
import hudson.model.Cause;
import hudson.model.Cause.UpstreamCause;
import hudson.util.RunList;
import jenkins.model.Jenkins;
import jenkins.model.TransientActionFactory;
import jenkins.model.GlobalConfiguration;

public class DownstreamBuildsAction implements Action {

    private RunList<Run> builds;

    public DownstreamBuildsAction(Run run, View view) {
        this.builds = findDownstreamBuilds(run, view);
    }

    private RunList<Run> findDownstreamBuilds(Run run, View view) {
        RunList<Run> builds = view.getBuilds();
        return builds.filter((Run b) -> isCausedBy(b, run));
    }

    private Boolean isCausedBy(Run candidate, Run parent) {
        Iterable<Cause> causes = candidate.getCauses();
        for (Cause c : causes) {
            if (!(c instanceof UpstreamCause)) continue;
            Run upstream = ((UpstreamCause)c).getUpstreamRun();
            if (upstream == null) {
              continue;
            } else if (upstream == parent) {
              return true;
            } else {
              return isCausedBy(upstream, parent);
            }
        }
        return false;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }

    public RunList<Run> getBuilds() {
        return this.builds;
    }

    @Extension
    public static class DownstreamBuildsActionFactory extends TransientActionFactory<Run> {

        @Override public Class<Run> type() {
            return Run.class;
        }

        @Override public Collection<DownstreamBuildsAction> createFor(Run run) {
            DownstreamBuildsConfig config = GlobalConfiguration.all().get(DownstreamBuildsConfig.class);
            if (config == null) return Collections.emptySet();
            String viewName = config.getViewName();
            View view = Jenkins.getInstance().getView(viewName);
            if (view == null) return Collections.emptySet();
            DownstreamBuildsAction action = new DownstreamBuildsAction(run, view);
            return Collections.singleton(action);
        }
    }
}
