## Attribute list per Ellement of a simulation

This file provides a list of all currently available ellemts that are supported
by the simulator and can be created using an *.xml* graph.

###Description of attirbutes ###

*id* _integer_
Represents the unique identifier of an object, eatch id should be unique. Ellements are
addressed using there ids.

*type* _integer_
Integer value representing the unique type of an object, used by the simulator.

*size* _integer_
Used in wires, represents the width of a wire, in other words the number of distinct
bits a wire can hold.

*path* _text_
Absolute path to the folder where the file is stored so it can be loaded by the simulator.

*file\_name* _text_
Name of the file containing the data of a node, data should be stored in binnary form with
eatch bit seperated by a dot.
Example :
```
1.1.1.1.0.0.0.0
```
Represents a binary 480.

*description* _text_
Equivalent of the _type_ field but used by the UI, see bellow for the corresponding descriptions
for all the objects.

*name* _text_
Name by witch the object will be refered to in the UI, you are advised to make this field
as precise as possible to prevent situations where an objects denomination becomes unclear.

*height* _integer_
Height of the representation of you objectm used in UI.

*width* _integer_
Weight of the representation of you objectm used in UI.

*pos\_x* _integer_
Position in x of the object in the UI.

*pos\_y* _integer_
Position in y of the object in the UI.

_Note_ :
Some ellements will only be exploited by the simulator and not by the user interface, this
is the case for wires and probes. Nevertheless they are necessary if you want to allow your
simulation to work correctly.


### Wire

Wires connecting the diffrent nodes in the simulation, this is where the wire values on
startup are stored.

_Note_
This object is only used by the simulator
so there is no need to specify here UI specific fields : name , description , position , height, width, ect ...

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

Object for setting values to a wire easily.

_Note_
This object is only used by the simulator
so there is no need to specify here UI specific fields : name , description , position , height, width, ect ...


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

Object for getting values from a wire easily.

_Note_
This object is only used by the simulator
so there is no need to specify here UI specific fields : name , description , position , height, width, ect ...


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

Data memory, also commenly refered to as "resisters", is where all of our working variable data is held.
It's data is represented in the text file designated but _path_ + _file name_. It's
capacity is of _memory block size_ * _memory number block_ with eatch value having a capacity of
_memory block size_ bits and there beeing _memory number block_ variables all stored on an unique line.

Attribute list :
- id _integer_
- type = **4** _integer_
- path _text_
<<<<<<< Updated upstream
- file\_name _text_
- memory\_block\_size _integer_
- memory\_number\_block _integer_
- description = **DM** _text_
- name _text_
- height _integer_
- width _integer_
- pos_x _integer_
- pos_y _integer_
=======
- file_name _text_
- memory\_block\_size _integer_
- memory\_number\_block _integer_
>>>>>>> Stashed changes

example:
```xml
<node id="8" type="4" path="/path/to/folder/" file_name="uniquename.txt" memory_block_size="14" memory_number_block="8"><!-- data memory node -->
        <wire_in id="1" description="register" name="write commande wire" />
        <wire_in id="2" description="register" name="read commande wire" />
        <wire_in id="13" description="register" name="address of op1"/>
        <wire_in id="15" description="register" name="address of op2"/>
        <wire_in id="3" description="register" name="input data"/>
        <wire_out id="4" description="register" name="output data at address op1"/>
        <wire_out id="11" description="register" name="out data at address op2"/>
</node>
```
### PC

Represents the programme counter node. The data will be stored in a file named <file_name> at location specied
by <path>.

Attribute list :
- id _integer_
- type = *5** _integer_
- path _text_
- file_name _text_
- memory\_block\_size _integer_

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
+ 6  _Text_
+ 7  _MUX_
+ 8  _DEMUX_

### ALU

Arythmetic logical unit to perform binary mathermatical opperations.

Attribute list :
- id _integer_
- type = **9** _integer_
- description = **ALU** _text_
- name _text_
- height _integer_
- width _integer_
- pos_x _integer_
- pos_y _integer_

```xml
<node id="17" type="9" control_bits="3" description ="ALU" name = "Algorithmic Logic Unit"
height="20" width="20" pos_x="50" pos_y="50" >

        <wire_in id="1" description="register" name="Input One ALU"
        height="20" width="20" pos_x="50" pos_y="50"/>

        <wire_in id="2" description="register" name="Input Two ALU"
        height="20" width="20" pos_x="50" pos_y="50"/>

        <wire_in id="3" description="register" name="Control ALU"
        height="20" width="20" pos_x="50" pos_y="50"/>

        <wire_out id="4" description="register" name="Output ALU"
        height="20" width="20" pos_x="50" pos_y="50"/>

</node>
```


+ 10 _ANDGATE_
+ 11 _ORGATE_
+ 12 _NOTGATE_
+ 13 _DECODER_
