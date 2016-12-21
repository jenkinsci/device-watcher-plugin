package com.pilz.pnozmulti.DeviceWatch;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import hudson.Extension;
import hudson.model.AdministrativeMonitor;

@Extension
public class PNOZMultiDeviceMonitor extends AdministrativeMonitor
{

   @Override
   public String getDisplayName()
   {
      return "PNOZMulti Device Watcher";
   }

   @Override
   public boolean isActivated()
   {
      DeviceGlobalConfiguration ds = new DeviceGlobalConfiguration().get();

      boolean reachable = false;
      for (String s : ds.getAddresses().split(","))
      {
         s = s.trim();
         System.out.println(s);

         try
         {
            try (Socket soc = new Socket())
            {
               soc.connect(new InetSocketAddress(s, 9000), 1000);
            }
            reachable = true;
         }
         catch (IOException ex)
         {
            reachable = false;
         }

         System.out.println(s + " is " + reachable);


      }

      return !reachable;
   }

}

/*
 * Copyright 2016 Pilz Ireland Industrial Automation Ltd.
 * All Rights Reserved. PILZ PROPRIETARY/CONFIDENTIAL.
 * Use is subject to license terms.
 * 
 * Created on 13 Dec 2016 by pbuckley
 */