# reactive-practice


#### install mongo docker 

`docker pull mongo`

`docker run -d -p 27017:27017 -v ~/data:/data/db --name mongoContainer mongo`

#### For get into the mongo console

`docker exec -it mongoContainer mongo`