== Icelandic Economy

Icelandic economy is merely a working name for a market data aggregator for eve
online. The intention is to create a website somewhat akin to eve-central, but 
with more data analysis tools. 

The project exists more to provide myself with learning opportunities than to 
ultimately create a viable application. That said however, I intend to write it
with the attention to security, detail, performance, and usability required such
that it could potentially serve as a competitor to eve central. After all, if I
am not taking my learning seriously, I am not learning as much as I could.

The project shall consist of 3 planned modules at this time: The first( this one ),
is a frontend for presentation of the aggregated data in its current state. It 
is meant to be searchable, indexable, and linkable. The second module is the
data analysis module which is planned to run as a separate application, which will
analayse the raw eve data in the database to provide information about cost-benefit
ratios, or whatever I can manage the creativity and mathematical understanding to
implement.

The final module is of course the Eve API parsing layer, which will schedule 
the GET requests from eve's carbon API, and then parse and store the results.

Perhaps it is not accurate to refer to these as modules, but rather applications,
as there will be submodules/dependencies that are included by all, such as the database
access layer.
