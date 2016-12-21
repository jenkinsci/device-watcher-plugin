package com.pilz.pnozmulti.DeviceWatch;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;

@Extension
public class DeviceGlobalConfiguration extends GlobalConfiguration
{

   private String addresses;


   public DeviceGlobalConfiguration()
   {
      load();
   }

   public String getAddresses()
   {
      return addresses;
   }

   public DeviceGlobalConfiguration get()
   {
      return GlobalConfiguration.all().get(DeviceGlobalConfiguration.class);
   }

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
 * Copyright 2016 Pilz Ireland Industrial Automation Ltd. All Rights Reserved. PILZ
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Created on 13 Dec 2016 by pbuckley
 */