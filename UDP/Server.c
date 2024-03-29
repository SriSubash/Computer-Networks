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

int main()
{
        int sockfd;
        char buffer[MAXLINE];
        struct sockaddr_in servaddr, cliaddr;

        if ( (sockfd = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
                perror("socket creation failed");
                exit(EXIT_FAILURE);
        }

        memset(&servaddr, 0, sizeof(servaddr));
        memset(&cliaddr, 0, sizeof(cliaddr));

        servaddr.sin_family = AF_INET;
        servaddr.sin_addr.s_addr = INADDR_ANY;
        servaddr.sin_port = htons(PORT);

        if ( bind(sockfd, (const struct sockaddr *)&servaddr,sizeof(servaddr)) < 0 )
        {
                perror("bind failed");
                exit(EXIT_FAILURE);
        }

        int len, n,T;
        len = sizeof(cliaddr);
        n = recvfrom(sockfd, &T, sizeof(int),MSG_WAITALL, ( struct sockaddr *) &cliaddr,&len);      
        while(T--)
        {
                n = recvfrom(sockfd, (char *)buffer, MAXLINE,MSG_WAITALL, ( struct sockaddr *) &cliaddr,&len);
                int n1=strlen(buffer),red=0,green=0,i;
                char *msg;
                if(buffer[0]=='R' && buffer[n1-1]=='R')
                {
                        red++;
                }
                else if(buffer[0]=='G' && buffer[n1-1]=='G')
                {
                        green++;
                }
                for(i=0;i<n;i++){
                        if(buffer[i]=='R' && buffer[i+1]=='R')
                        {
                                red++;
                        }
                        else if(buffer[i]=='G' && buffer[i+1]=='G')
                        {
                                green++;
                        }
                }
                if(green==1 && red==1 || green==0 && red==0)
                        msg="Garland can be Beautified\n";
                else
                        msg="Garland cannot be Beautified\n";
                printf("Hii\n");
                sendto(sockfd, (char *)msg, strlen(msg),MSG_CONFIRM, (const struct sockaddr *) &cliaddr,sizeof(cliaddr));
        }

        return 0;
}