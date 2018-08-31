#include "mfhdf.h"

int main() {
    uint32 major;
    uint32 minor;
    uint32 release;
    char s[MAX_NC_NAME];

    Hgetlibversion(&major,&minor,&release,s);

    return 0;
}
