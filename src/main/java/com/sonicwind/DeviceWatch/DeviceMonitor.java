package com.sonicwind.DeviceWatch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;

import hudson.Extension;
import hudson.model.AdministrativeMonitor;

@Extension
public class DeviceMonitor extends AdministrativeMonitor
{
   ArrayList<String> offline = new ArrayList<>();

   Timer scanTimer = null;

   @Override
   public String getDisplayName()
   {
      return "Device Watcher";
   }

   @Override
   public boolean isActivated()
   {

      if (!isEnabled())
      {
         return false;
      }

      if (scanTimer == null)
      {
         scanTimer = new Timer(true);
         scanTimer.scheduleAtFixedRate(new ScanTimer(), 0, 10000);
      }

      return !offline.isEmpty();
   }

   public ArrayList<String> getListOffline()
   {
      return offline;
   }

   private class ScanTimer extends TimerTask
   {

      @Override
      public void run()
      {
         ArrayList<String> offlineDevices = new ArrayList<>();
         LinkedHashSet<String> device = new LinkedHashSet<>();
         DeviceGlobalConfiguration ds = new DeviceGlobalConfiguration().get();

         if (ds == null || ds.getAddresses() == null)
         {
            return;
         }

         String unfiltered[] = ds.getAddresses().split(",");

         for (String unfilteredDevice : unfiltered)
         {

            unfilteredDevice = unfilteredDevice.trim();
            if (unfilteredDevice.isEmpty())
            {
               continue;
            }
            device.add(unfilteredDevice);

         }

         offlineDevices.clear();
         for (String s : device)
         {
            System.out.println("Checking: " + s);

            try
            {
               InetAddress host = InetAddress.getByName(s);


               if (!host.isReachable(10000))
               {
                  System.out.println(s + "was unreachable");
                  offlineDevices.add(s);
               }
            }
            catch (UnknownHostException ue)
            {
               ue.printStackTrace();
            }
            catch (IOException ex)
            {
               ex.printStackTrace();
               System.out.println(s + " has an IO Exception");
               if (!offline.contains(s))
               {
                  offlineDevices.add(s);
               }
            }


         }

         offline = offlineDevices;
      }
   }

}

/*
 * Copyright 2016 Pilz Ireland Industrial Automation Ltd. All Rights Reserved. PILZ
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Created on 13 Dec 2016 by pbuckley
 */