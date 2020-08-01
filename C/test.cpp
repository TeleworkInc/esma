#include <iostream>
#include <chrono>
using namespace std::chrono;

int main() {
    timespec ts;
    std::cout << high_resolution_clock::now;
    return 0;
}