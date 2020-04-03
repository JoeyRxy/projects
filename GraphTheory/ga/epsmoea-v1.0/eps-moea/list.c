/* A custom doubly linked list implemenation */

# include <stdio.h>
# include <stdlib.h>
# include <math.h>

# include "global.h"
# include "rand.h"

/* Routine to insert an element after the location specified by node NODE */
void insert (ind_list *node, individual *ind)
{
    ind_list *temp;
    if (node==NULL)
    {
        printf("\n Error!! asked to enter after a NULL pointer, hence exiting \n");
        exit(1);
    }
    temp = (ind_list *)malloc(sizeof(ind_list));
    temp->ind = (individual *)malloc(sizeof(individual));
    allocate (temp->ind);
    copy (ind, temp->ind);
    temp->child = node->child;
    temp->parent = node;
    if (node->child != NULL)
    {
        node->child->parent = temp;
    }
    node->child = temp;
    elite_size++;
    return;
}

/* Delete the element specified by node NODE */
ind_list* del (ind_list *node)
{
    ind_list *temp;
    if (node==NULL)
    {
        printf("\n Error!! asked to delete a NULL pointer, hence exiting \n");
        exit(1);
    }
    temp = node->parent;
    temp->child = node->child;
    if (temp->child!=NULL)
    {
        temp->child->parent = temp;
    }
    deallocate(node->ind);
    free (node->ind);
    free (node);
    elite_size--;
    return (temp);
}
