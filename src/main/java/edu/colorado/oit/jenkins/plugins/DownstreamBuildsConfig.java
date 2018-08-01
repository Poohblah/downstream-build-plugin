package edu.colorado.oit.jenkins.plugins;

import hudson.Extension;
import hudson.model.View;
import hudson.util.ListBoxModel;
import jenkins.model.Jenkins;
import jenkins.model.GlobalConfiguration;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import net.sf.json.JSONObject;

@Extension
public class DownstreamBuildsConfig extends GlobalConfiguration {

    private String viewName = "All";

    public DownstreamBuildsConfig() {
        load();
    }

    public static DownstreamBuildsConfig get() {
        return GlobalConfiguration.all().get(DownstreamBuildsConfig.class);
    }

    @DataBoundSetter
    public void setViewName(String viewName) {
        this.viewName = viewName;
        save();
    }

    public String getViewName() {
        return viewName;
    }

    public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
        req.bindJSON(this, formData);
        save();
        return false;
    }

    public ListBoxModel doFillViewNameItems() {
        ListBoxModel items = new ListBoxModel();
        Iterable<View> views = Jenkins.getInstance().getViews();
        for (View view : views) {
            String name = view.getDisplayName();
            items.add(name);
        }
        return items;
    }

    @Override
    public String getDisplayName() {
        return "Downstream Builds";
    }
}
