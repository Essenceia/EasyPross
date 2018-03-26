## Attribute list per Ellement of a simulation

This file provides a list of all currently available ellemts that are supported
by the simulator and can be created using an *.xml* graph.

### Wire

short desc

Attribute list :
- id _integer_
- type = **1** _integer_
- size _integer_

example:
```xml
<wire id="1" type="1" size="3">
		<io> 0 </io><!-- value of data on LSB (least significant bit), bit 0-->
		<io> 0 </io><!-- value of data on bit 1 -->
		<io> 1 </io><!-- value of data on MSB (most significant bit) bit 2 -->
</wire>
```

### Probe Start

short desc

Attribute list :
- id _integer_
- type = **2** _integer_

example:
```xml
<probe id="40" type="2" >
		<wire id="1"/><!-- wire connected to this probe -->
</probe>
```
### Probe End

short desc

Attribute list :
- id _integer_
- type = **3** _integer_

example:
```xml
<probe id="21" type="3" >
		<wire id="7"/><!-- wire connected to this probe -->
</probe>
```

### Data

short desc

Attribute list :
- id _integer_
- type = **4** _integer_
- path _text_
- file_name _text_

example:
```xml
<node id="8" type="4" path="/path/to/folder/" file_name="uniquename.txt" memory_block_size="14" memory_number_block="8"><!-- data memory node -->
        <wire_in id="1" /><!--write commande wire-->
        <wire_in id="2" /><!--read commande wire-->
        <wire_in id="13" /><!--address of op1-->
        <wire_in id="15" /><!--address of op2-->
        <wire_in id="3" /><!--input data-->
        <wire_out id="4" /><!--output data at address op1-->
        <wire_out id="11" /><!--out data at address op2-->
</node>
```

+ 5  _PC_
+ 6  _Text_
+ 7  _MUX_
+ 8  _DEMUX_
+ 9  _ALU_
+ 10 _ANDGATE_
+ 11 _ORGATE_
+ 12 _NOTGATE_
+ 13 _DECODER_
