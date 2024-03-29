#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int main() {
    int client_fd,i,j;
    struct sockaddr_in server_addr;
    int mat[50][50],N;

    client_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (client_fd == -1) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY; // Change to server IP
    server_addr.sin_port = htons(8878);

    if (connect(client_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Connection failed");
        exit(EXIT_FAILURE);
    }
    int k;
    printf("Enter the Size of the Square Matrix :\n");
    scanf("%d",&N);
    printf("Enter the size of the Submatrix :\n");
    scanf("%d",&k);
    send(client_fd,&N, sizeof(int), 0);
    send(client_fd,&k, sizeof(int), 0);
    printf("Enter the Elements :\n");
    for(i=0;i<N;i++)
    {
        for(j=0;j<N;j++)
        {
                scanf("%d",&mat[i][j]);
        }
    }
    for(i=0;i<N;i++)
    {
        for(j=0;j<N;j++)
        {
                send(client_fd,&mat[i][j], sizeof(int), 0);
        }
    }
    close(client_fd);

    return 0;
}