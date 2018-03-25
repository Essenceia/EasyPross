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

# TODO on 23 mars #

- [ ] Connect API
    - [ ] Reset 
    - [x] <del> evrything else </del>
- [ ] test API
- [ ] test PC file modification on timestamp out-off-data
- [x] add file interaction on all register models
    - <del>[x] prog memory read </del> doesn't exist in this model(genius) :octocat: 
    - [x] prog memory write
- [ ] Start building more complex modules to start heading towards the easy pross diagrame 
    - [x] <del>Mux*2 + Alu</del>
    - [ ] Mux*2 + ALU + Decode _Nico_
    - [ ] Pc + Prog + Decode
        - [ ] debug -> not working
    - [ ] Decode + Data + Mux*2 + Pc 
