# Laboratory Work #1
### Checkpoint #1
For the first checkpoint, it was necessary to draw a diagram of the logic of the program for processing **SSE** tweets.
The steps of the work are carried out as follows:
1. The so-called `Chainer` connects to two streams
to receive messages from them.  Here the messages
do not immediately go to be processed in another
part of the program. The `Chainer` puts all the messages
it received in a second into the buffer and only then sends
them to `Worker Supervisor`.
2. In `Worker Supervisor`, **workers** are created, the number of
which depends on the number of messages received from `Chainer` in a one-to-one
ratio. These **workers** are also placed in the buffer, and 
then sent along with messages to the `Manager` at the same time.
3. The next step `Manager` iterates both buffers of **workers** and
messages that he received from `Worker Supervisor`.  During
the iteration, the `Manager` sends each worker its own message.
4. After receiving the message, **worker** processes it, outputs it, and then self-deletes it.

<img src="https://i.ibb.co/hKX8kjf/Edited.png" width=300>

* Also, there is the Main `Supervisor` which creates
some actors that there had been discussed above such as
`Manager`, `Chainer` and `Worker Supervisor`. 

<img src="https://i.ibb.co/3Nctxp7/MainSup.png" width=300>