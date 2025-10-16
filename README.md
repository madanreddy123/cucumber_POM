	1.	Limited VM RAM – The current memory allocation appears insufficient for the number of concurrent browser sessions we’re running. Increasing the RAM should help improve stability and reduce test flakiness.
	2.	Limited Bamboo Agents – We currently have a small number of agents available for automation builds, which is causing build queues and missed scheduled runs.

Could we please consider:
	•	Increasing the VM’s RAM allocation, and
	•	Adding or redistributing Bamboo agents to better support automation builds?