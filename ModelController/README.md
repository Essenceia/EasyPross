# TODO for 21 mars #

- [x] <del>test XML construction strill works</del>
- [x] <del>add pc node creation to xml graph manager </del>
- [x] <del>correct for missing wire_in</del>
- [x] <del>correct for uncorrect values on wires save</del>


# TODO on 21 mars #

- [x] <del>test PC file modifications from write wire</del>
- [x] <del>add decoder to xml</del>
- add file interaction on all register models
    - [x] data memory read 
    - [x] <del>data memory write</del>


# Testing list #

- [x] Mux
- [x] Demux
- [x] Alu ( control codes)
    -[x] 000
    -[x] 001
    -[x] 010
    -[x] 011
    -[x] 100
    -[x] 101
    -[x] 110
    -[x] 111
- [x] writring to probe start
- [x] reading from probe end

# TODO #
- [ ] Update communication protocol : remove "," between opcode and beging of datamessage
- [ ] Update communication protocol : 7 : id path / response : id path check" 
- [ ] re-write api :
    - [x] load module
    - [x] simulate tick
    - [x] get data on id
    - [x] set data on id
    - [x] reset
    - [x] get file abs path
    - [ ] reload the data file on said node id
    - [ ] debug option to save to xml
- [ ] check out error on test_2 when reset is called
- [ ] test PC file modification on timestamp out-off-data
- [x] add file interaction on all register models
    - <del>[x] prog memory read </del> doesn't exist in this model(genius) :octocat: 
    - [x] prog memory write
- [ ] Start building more complex modules to start heading towards the easy pross diagrame 
    - [x] <del>Mux*2 + Alu</del>
    - [ ] Mux*2 + ALU + Decode _Nico_
    - [x] Pc + Prog + Decode
        - [x] debug -> not working
        - [ ] verify test correctness
    - [ ] Decode + Data + Mux*2 + Pc
    
_PS: Nico , Mathieu pour faire les testes une interface de commande pour l'API peut etre trouver :
[Interface de Commande](https://github.com/Essenceia/EasyPross_Consol_Commande_Interface)_
