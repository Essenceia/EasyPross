## Troublshooting your XML 

Here a couple of recurring errors commenly found when designing and XML component for the simulator.

* Forgottent attributes : Check that you have correctly filled all of the necessary attributes required by the type of component you are designing
* Duplicate id, ellements of the simulation are addressed by id so double check to make sure only one ellement exists at eatch id.
* Missmatch in id. This can happen when linking a wire with a probe or a node, if the id's don't match the wires will not be connected correclty in your simulation.
* Duplicate file name, if 2 nodes are using the same file this can creat errors in block number and block sizes resulting in acceses to undefined ellements. Also make shure that 2 diffrent simulation are not using similar file names.
