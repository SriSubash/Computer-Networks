#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

int main() {
    int server_socket, client_socket1, client_socket2;
    struct sockaddr_in server_addr, client_addr;
    socklen_t client_len = sizeof(client_addr);

    // Create socket
    server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket == -1) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    // Bind
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(8010);
    server_addr.sin_addr.s_addr = INADDR_ANY;
    if (bind(server_socket, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Binding failed");
        exit(EXIT_FAILURE);
    }

    // Listen
    if (listen(server_socket, 2) == -1) {
        perror("Listening failed");
        exit(EXIT_FAILURE);
    }

    int pid=fork();
    if (pid > 0) {
        client_socket1 = accept(server_socket, (struct sockaddr *)&client_addr, &client_len);
        int a[3],i;
        for(i=0;i<3;i++)
           recv(client_socket1,&a[i],sizeof(int),0);
        int sum=a[0]*a[0]+a[1]*a[1]+a[2]*a[2];
        send(client_socket1, &sum, sizeof(int), 0);

    } else {
        client_socket2 = accept(server_socket, (struct sockaddr *)&client_addr, &client_len);
        int b[6],j;
        for(j=0;j<6;j++)
           recv(client_socket2,&b[j],sizeof(int),0);
        int sum1=b[0]+b[1]+b[2]+b[3]+b[4]+b[5];
        send(client_socket2,&sum1,sizeof(int), 0);
    }
    close(client_socket1);
    close(client_socket2);
    //close(server_socket);

    return 0;
}