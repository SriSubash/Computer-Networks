#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#define PORT     8780
#define MAXLINE 1024

int main() {
        int sockfd;
        char buffer[MAXLINE];
        char hello[100];
        struct sockaddr_in       servaddr;

        if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
                perror("socket creation failed");
                exit(EXIT_FAILURE);
        }

        memset(&servaddr, 0, sizeof(servaddr));

        servaddr.sin_family = AF_INET;
        servaddr.sin_port = htons(PORT);
        servaddr.sin_addr.s_addr = INADDR_ANY;

        int n, len=sizeof(servaddr);
        printf("Enter the No of Test Cases :\n");
        int T;
        scanf("%d",&T);
        sendto(sockfd, &T, sizeof(int),MSG_CONFIRM, (const struct sockaddr *) &servaddr,sizeof(servaddr));
        while(T--)
        {
           printf("Enter the Flower Order :\n");
           scanf("%s",hello);
           sendto(sockfd, (char *)hello, strlen(hello),MSG_CONFIRM, (const struct sockaddr *) &servaddr,sizeof(servaddr));
           n = recvfrom(sockfd, (char *)buffer, MAXLINE,MSG_WAITALL, ( struct sockaddr *) &servaddr,&len);
           printf("%s",buffer);
        }
        close(sockfd);
        return 0;
}