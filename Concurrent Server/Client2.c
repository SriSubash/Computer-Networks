#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
    int client_fd,sum=0;
    struct sockaddr_in server_addr;

    // Create socket
    client_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (client_fd == -1) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Set up server address
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY; // Change to server IP
    server_addr.sin_port = htons(8010);

    // Connect to server
    if (connect(client_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Connection failed");
        exit(EXIT_FAILURE);
    }
    int a[6],i;
    printf("Enter the Index Positions :\n");
    for(i=0;i<6;i++)
       scanf("%d",&a[i]);
    for(i=0;i<6;i++)
        send(client_fd, &a[i], sizeof(int), 0);
	close(client_fd);

    return 0;
}