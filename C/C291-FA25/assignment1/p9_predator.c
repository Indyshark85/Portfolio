#include <stdio.h>


int main(){
double alpha=0.1,beta=0.02,delta=0.01,gamma=0.1,x=40.0,y=9.0,dt=0.01,N=1000.0;

for (int t=1; t<=N;t++){
double dx = (alpha*x-beta*x*y)*dt;
double dy = (delta*x*y-gamma*y)*dt;
x=x+dx;
y=y+dy;



printf("Time step: %d, Prey (x): %.2f, Predators (y): %.2f\n", t, x, y);
}
return 0;

}
