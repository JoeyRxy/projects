"""
    Functions:
        find_path:
    Data structures:
        path_vertexs：保存遍历过的顶点，防止重复遍历
        path_length：保存遍历过的每条边的权值
"""
def find_path(j):
    path_vertexs.append(j)  #把该节点标记为已走过    
    row=c[j]  
    #创建copy_row,删除row中已走过的顶点,防止直接在row上操作.
    copy_row=[value for value in row]
    walked_vertex=[]
    for i in path_vertexs:#已走过的顶点
        walked_vertex.append(copy_row[i])
    for vertex in walked_vertex:
        copy_row.remove(vertex)
    #寻找row中的未遍历过的最短边
    if len(path_vertexs)<5:
        min_e=min(copy_row)
        j=row.index(min_e)
        path_length.append(min_e)
        find_path(j)
    else:
        min_e=c[j][0]
        path_length.append(min_e)
        path_vertexs.append(0)
    return path_vertexs,path_length

def print_path(vertexs,lengths):
    path=''    
    vertexs=[vertex+1 for vertex in vertexs]
    for i,vertex in enumerate(vertexs):
        path+=str(vertex)
        if i==5:
            break
        path+='->'
    print("最小总旅费为：",sum(lengths))
    print("路 径：",path)

if __name__ == "__main__":
    c=[[0,3,1,5,8],
    [3,0,6,7,9],
    [1,6,0,4,2],
    [5,7,4,0,3],
    [8,9,2,3,0]]
    path_length=[]
    path_vertexs=[]   
    path_vertexs,path_length=find_path(0)
    print_path(path_vertexs,path_length)
    
"""    从顶点0开始：
        把0加入到已遍历集合，寻找第0行的最小值，并返回其所在列j，
    转到第j行，寻找未遍历过的最短边。
        把顶点加入已遍历集合，保存对应的最小权值。 
    停止： 当走过的顶点集长度=5时，停止
"""