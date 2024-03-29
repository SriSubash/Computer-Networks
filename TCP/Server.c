#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
int main() {
    int server_fd, client_fd,i,j;
    struct sockaddr_in server_addr, client_addr;
    socklen_t addr_len = sizeof(client_addr);
    int N;

    server_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (server_fd == -1) {
        perror("Socket creation failed");
        exit(EXIT_FAILURE);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_port = htons(8878);

    if (bind(server_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) == -1) {
        perror("Binding failed");
        exit(EXIT_FAILURE);
    }

    if (listen(server_fd, 5) == -1) {
        perror("Listening failed");
        exit(EXIT_FAILURE);
    }
    client_fd = accept(server_fd, (struct sockaddr *)&client_addr, &addr_len);
    if (client_fd == -1) {
            perror("Accepting connection failed");
    }
    int k;
    recv(client_fd,&N, sizeof(int), 0);
    recv(client_fd,&k, sizeof(int), 0);
    int mat[N][N];
    for(i=0;i<N;i++)
    {
        for(j=0;j<N;j++)
        {
                recv(client_fd,&mat[i][j], sizeof(int), 0);
        }
    }
        if (k > N)
                return;

        int stripSum[N][N];

        for (j = 0; j < N; j++) {

                int sum = 0;
                for ( i = 0; i < k; i++)
                        sum += mat[i][j];
                stripSum[0][j] = sum;

                for (i = 1; i < N - k + 1; i++) {
                        sum += (mat[i + k - 1][j] - mat[i - 1][j]);
                        stripSum[i][j] = sum;
                }
        }

        int max_sum = 0, *pos = NULL;

        for ( i = 0; i < N - k + 1; i++) {

                int sum = 0;
                for (j = 0; j < k; j++)
                        sum += stripSum[i][j];

                if (sum > max_sum) {
                        max_sum = sum;
                        pos = &(mat[i][0]);
                }

                for (j = 1; j < N - k + 1; j++) {
                        sum += (stripSum[i][j + k - 1]
                                        - stripSum[i][j - 1]);
                        if (sum > max_sum) {
                                max_sum = sum;
                                pos = &(mat[i][j]);
                        }
                }
        }
        printf("The Given Matrix is :\n");
        for (i = 0; i < N; i++) {
                for (j = 0; j < N; j++){
                        printf("%d ",mat[i][j]);
                }
                printf("\n");
        }
        printf("The Maximum Sum Submatrix is :\n");
        for (i = 0; i < k; i++) {
                for (j = 0; j < k; j++){
                        printf("%d ",*(pos + i * N + j));
                }
                printf("\n");
        }
        close(client_fd);
        return 0;
}