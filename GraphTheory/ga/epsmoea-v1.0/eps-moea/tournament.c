/* Tournamenet Selections routine */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Routine for binary tournament */
individual* tournament (individual *ind1, individual *ind2)
{
    int flag;
    flag = check_dominance (ind1, ind2);
    if (flag==1)
    {
        return (ind1);
    }
    if (flag==-1)
    {
        return (ind2);
    }
    if ((randomperc()) <= 0.5)
    {
        return(ind1);
    }
    else
    {
        return(ind2);
    }
}
