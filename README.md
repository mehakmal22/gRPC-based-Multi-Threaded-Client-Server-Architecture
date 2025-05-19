# gRPC-based-Multi-Threaded-Client-Server-Architecture
Pre-Requisites: Make sure you have the Python, Java, and docker desktop installed on your system.

Follow the steps below to run my code:

1. Download the zip file I've submitted into your system and unzip it. It contains a total of 5 files(Dockerfile_server, Dockerfile_client, grpc_server.py, ImageSearchClient.java & image search.proto) and one folder named "main_images" in which there are images of cat, dog, fox, lion and zebra.

2. Open powershell in your system and move to the docker directory. Then, execute the command "pip install grpcio grpcio-tools" to install "grpcio" and "grpcio-tools" packages.

3. Next, run the command "python -m grpc_tools.protoc -I. --python_out=. --grpc_python_out=. imagesearch.proto to compile imagesearch Protocol Buffer (.proto) file into Python code that can be used with gRPC.

4. Make sure that two files named imagesearch_pb2.py and imagesearch_pb2_grpc.py are created within the docker directory.

5. Then, run the following commands to build and run the server docker image:
   "docker build -t grpc-image-search-server -f Dockerfile_server ."
   "docker run --network="host" grpc-image-search-server"

6. Then, open a new terminal and run the following commands to build and run the client docker image:
   "docker build -t grpc-image-search-client -f Dockerfile_client ."
   "docker run --network="host" -v $(pwd)/client_output:/app/received_images_grpc -it grpc-image-search-client"
   After running the above commands, you will get a prompt "Enter the object class (keyword) to search for:" and you can enter any one from the car, dog, fox, lion and zebra.
   Then, go to the docker folder, where you can see a folder named "client_output" in which the image you desired will be there. 
