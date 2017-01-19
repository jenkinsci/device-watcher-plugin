# Jenkins Device Watcher Plugin
A plugin to notify an administrator when an IP Address becomes unreachable,
this is useful for ensuring embedded devices are up, a core server is available, etc.

## Usage
When the IP addresses you wish to watch goes down, an administitive notification will be created.
The IP addresses can be set in the global configuration of Jenkins.

## Future Goals

1. Allow two types of scans, ICMP and TCP Syn
2. Allow the scan to happen on any slave. (Useful for an embedded device connected to a slave)
3. Build step to fail builds if a device isin't available
