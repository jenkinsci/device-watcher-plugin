package com.sonicwind.DeviceWatch;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

/**
 * *******************************************************************
 * 
 * Device Watcher global configuration
 * 
 * @author pbuckley
 * @version 0.8
 ********************************************************************
 */
@Extension
public class DeviceGlobalConfiguration extends GlobalConfiguration
{

   // Comma Seperated list of addresses
   private String addresses;

   // When the class is created, load the saved state
   public DeviceGlobalConfiguration()
   {
      load();
   }

   // Get the configured addresses
   public String getAddresses()
   {
      return addresses;
   }

   // Get the Global Config from Global itself
   public DeviceGlobalConfiguration get()
   {
      return GlobalConfiguration.all().get(DeviceGlobalConfiguration.class);
   }

   // On configure, save our addresses
   @Override
   public boolean configure(StaplerRequest req, JSONObject json)
         throws hudson.model.Descriptor.FormException
   {
      addresses = json.getString("addresses");
      save();
      return true;
   }

}

/*
 * Created on 13 Dec 2016 by pbuckley
 */