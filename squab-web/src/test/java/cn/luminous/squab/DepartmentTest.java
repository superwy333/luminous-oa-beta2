package cn.luminous.squab;


import cn.luminous.squab.entity.Department;
import cn.luminous.squab.service.DepartmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    public void test() {

        // 所有的部门
        //Department department = new Department();
        List<Department> allDepts = departmentService.query(new Department());

        allDepts.stream().forEach(department -> {
            if (department.getPid()!=0) {
                // 处理本部门领导
                if (department.getLeader()==null) {
                    String leader = getLeader(department);
                    //System.out.println(department.getName() + " leader is： " + leader);
                    department.setLeader(leader);
                    //departmentService.updateByIdSelective(department);
                }

                // 处理上级部门领导
                if (department.getParentLeader()==null) {
                    Long parentLeader = getParentLeader(department);
                    department.setParentLeader(parentLeader);
                }

                // 处理分管领导
                if (department.getLeaderBranch()==null) {
                    String brachLeader = getBranchLeader(department);
                    department.setLeaderBranch(brachLeader);
                }


                departmentService.updateByIdSelective(department);



            }
        });

    }

    private String getLeader(Department department) {
        if (department.getLeader()==null) {
            Department father = departmentService.queryById(Long.valueOf(department.getPid()));
            if (father.getLeader()==null) {
                return getLeader(father);
            }else {
                //department.setLeader(father.getLeader());
                return father.getLeader();
            }
        } else {
            return department.getLeader();
        }

    }

    private Long getParentLeader(Department department) {
        if (department.getParentLeader()==null) {
            Department father = departmentService.queryById(Long.valueOf(department.getPid()));
            if (father.getLeader()==null) {
                return Long.valueOf(getLeader(father));
            }else {
                //department.setLeader(father.getLeader());
                return Long.valueOf(father.getLeader());
            }
        }else {
            return department.getParentLeader();
        }

    }

    private String getBranchLeader(Department department) {
        if (department.getPid()==0) {
            return null;
        }
        if (department.getLeaderBranch()==null) {
            Department father = departmentService.queryById(Long.valueOf(department.getPid()));
            if (father.getLeaderBranch()==null) {
                return getBranchLeader(father);
            }else {
                return father.getLeaderBranch();
            }
        }else {
            return department.getLeaderBranch();
        }
    }
}
