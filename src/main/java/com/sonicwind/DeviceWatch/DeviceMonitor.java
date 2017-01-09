package com.sonicwind.DeviceWatch;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Timer;
import java.util.TimerTask;

import hudson.Extension;
import hudson.model.AdministrativeMonitor;

/**
 * *******************************************************************
 * 
 * This class has multiple uses:
 * 
 * 1) Manages the Scanner Task
 * 
 * 2) Decides if the popup should be shown
 * 
 * 3) Decides what should be in the popup
 * 
 * @author pbuckley
 * @version 0.8
 ********************************************************************
 */
@Extension
public class DeviceMonitor extends AdministrativeMonitor
{
   private ArrayList<String> offline = new ArrayList<>();

   private Timer scanTimer = null;

   /**
    * Name of the Administrative Monitor
    */
   @Override
   public String getDisplayName()
   {
      return "Device Watcher";
   }

   /**
    * Should the Administrative Monitor show on all of the Jenkins Pages?
    */
   @Override
   public boolean isActivated()
   {
      // Return Early if it isint enabled.
      if (!isEnabled())
      {
         return false;
      }

      // Only set up one timer
      if (scanTimer == null)
      {
         scanTimer = new Timer(true);

         // Timer runs every 10 seconds
         scanTimer.scheduleAtFixedRate(new ScanTimer(), 0, 10000);
      }

      // Only show if we have offline devices
      return !offline.isEmpty();
   }

   /**
    * JELLY METHOD:
    * 
    * This method allows the jelly script to get the list of offline devices
    * 
    * @return list of offline devices
    */
   public ArrayList<String> getListOffline()
   {
      return offline;
   }

   /**
    * *******************************************************************
    * 
    * Thread that scans all of the hosts found in the global configuration
    * 
    * @author pbuckley
    * @version 0.8
    ********************************************************************
    */
   private class ScanTimer extends TimerTask
   {

      @Override
      public void run()
      {
         // Store the offline devices
         ArrayList<String> offlineDevices = new ArrayList<>();

         // This is used to implicitly remove any duplicates from the CSL given by the user
         LinkedHashSet<String> devices = new LinkedHashSet<>();

         // Get the global config
         DeviceGlobalConfiguration globalConfig = new DeviceGlobalConfiguration().get();

         // Sanity Check global config
         if (globalConfig == null || globalConfig.getAddresses() == null)
         {
            return;
         }

         // unsanitized device list
         String unsanitized[] = globalConfig.getAddresses().split(",");

         /*
          * This loop trims the string, ensures each host isin't empty or contains a space.
          * 
          * Finally, it is added to a set that doesnt allow duplicates.
          */
         for (String device : unsanitized)
         {
            device = device.trim();
            if (!device.isEmpty() && !device.contains(" "))
            {
               devices.add(device);
            }
         }

         // Ensure the list is empty
         offlineDevices.clear();

         // Scan each device with ICMP
         for (String device : devices)
         {
            try
            {
               InetAddress host = InetAddress.getByName(device);

               if (!host.isReachable(1000))
               {
                  offlineDevices.add(device);
               }
            }
            catch (IOException ex)
            {
               ex.printStackTrace();

               if (!offlineDevices.contains(device))
               {
                  offlineDevices.add(device);
               }
            }

         }

         // Set the global object (dangerous I know)
         offline = offlineDevices;
      }
   }

}

/*
 * Created on 13 Dec 2016 by pbuckley
 */