%-------------------------------------------------------------------------%
% SDS_INFO.m
%-------------------------------------------------------------------------%
% Auteur: Aminata NDIAYE, CGTD ICARE 2013
%-------------------------------------------------------------------------%
% Version: 0
%-------------------------------------------------------------------------%




classdef SDS_INFO
    properties (SetAccess = private, GetAccess = public)
      file_name;
      sds_name;
      number_of_dim;
      size_of_dim;
      sds_datatype;
      number_of_attributes;
    end
    
    methods

        % Create empty constructor and initializing constructor
        function self = SDS_INFO ( file_name, sds_name, number_of_dim, size_of_dim, sds_datatype, number_of_attributes)
            if nargin == 0
                self.file_name = ''  ;
                self.sds_name =  '';
                self.number_of_dim =0 ;
                self.size_of_dim = 0;
                self.sds_datatype = '';
                self.number_of_attributes = 0;
            else  
                self.file_name = file_name  ;
                self.sds_name =  sds_name;
                self.number_of_dim =number_of_dim ;
                self.size_of_dim = size_of_dim;
                self.sds_datatype = sds_datatype;
                self.number_of_attributes = number_of_attributes;
            end                
        end
        %------------------------------------       
    end
    
    methods (Static)
        
        % Open the hdf file
        function sd_id = open_hdf_file (filename )  
            
            sd_id = isempty(filename) ;
            if sd_id == 1
                error('Filename is null'); 
            end
            
            sd_id = hdfh('ishdf', filename) ;
            if   sd_id == 0
                error('It is not a hdf file')     
            end
            
            sd_id =  hdfsd( 'start', filename,  'rdonly');
            if sd_id < 0
                error ('Fail to open the hdf file')
            end
        end
        %------------------------------------
        
        % Close the hdf file
        function close_hdf_file (sd_id)
            status  =  hdfsd('end',sd_id) ;
            if status < 0
                error ('Fail to close the hdf file')
            end     
        end
        %------------------------------------
        
         % Close sds access
        function close_sds_access (sds_id)
            status  =  hdfsd('endaccess',sds_id) ;
            if status < 0
                error ('Fail to close sds access')
            end     
        end
        %------------------------------------ 
        
        % Test status value
        function test_status(var, status)
            if status == -1
                error('Funtion %s failed', var);
            end
        end
        %------------------------------------
        
        % Get the sds id number
        function sds_id = get_id_from_sdsname(sd_id, sds_name)
            sds_index  = hdfsd('nametoindex', sd_id, sds_name) ;
            sds_id = hdfsd('select', sd_id, sds_index);
        end
        %------------------------------------
        
        % Display attributes caracteristics 
        function get_attributes_info( id, number_of_attributes)
             for i = 0: number_of_attributes - 1
               [attribute_name,dtype,count,status] = hdfsd('attrinfo', id, i);
               SDS_INFO.test_status('hdfsd[attrinfo]', status);
               attribute_value = hdfsd('readattr', id, i);
               sprintf('Attribute name: %s\nData type: %s\nCount :%s\nAttribute value: %s\n', attribute_name, dtype,...
                            num2str(count), num2str(attribute_value))
             end
        end 
           
        % Create structure attribute  
        function create_attr ( id, number_of_attributes, attr, attr_name)
             if  isempty(attr_name)  ~= 1 
                 % attr_name existe              
                 var = hdfsd('findattr', id, attr_name);
                 start_cpt = var;
                 end_cpt = var;
             else
                 start_cpt = 0;
                 end_cpt = number_of_attributes - 1;
             end
             
         for i = start_cpt :   end_cpt            
                    [attr(i+1,1).name, attr(i+1,1).dtype, attr(i+1,1).count, attr(i+1,1).status] = hdfsd('attrinfo', id, i);
                    SDS_INFO.test_status('hdfsd[attrinfo]', attr(i+1,1).status);
                    attr(i+1,1).value = hdfsd('readattr', id, i);
                    sprintf('Attribute name: %s\nData type: %s\nCount :%s\nAttribute value: %s\n',attr(i+1,1).name,...
                        attr(i+1,1).dtype, num2str(attr(i+1,1).count), num2str(attr(i+1,1).value))
         end
             
        end                    
        %------------------------------------
        
         % Get caracteristics for all attributes of a sds or for a specific one
        function attr = get_sds_attr (filename, sds_name, attr_name)
            sd_id =  SDS_INFO.open_hdf_file(filename); 
            sds_id = SDS_INFO.get_id_from_sdsname (sd_id, sds_name);
            attr = struct([]);
            [name, num_dim, sds_dim,sds_datatype, num_attr] = hdfsd('getinfo', sds_id);
            SDS_INFO.create_attr( sds_id, num_attr,  attr,attr_name );           
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
        end
        
        % Get calibration of a sds
        function calibration = get_sds_calibration(filename, sds_name)
            sd_id =  SDS_INFO.open_hdf_file(filename); 
            sds_id = SDS_INFO.get_id_from_sdsname (sd_id, sds_name);
            [calibration.cal, calibration.cal_err, calibration.offset, calibration.offset_err, calibration.sds_datatype, calibration.status]...
                = hdfsd('getcal',sds_id); 
            SDS_INFO.test_status('hdfsd[getcal', calibration.status);
            disp(calibration);
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
        end
        %------------------------------------
        
        % Get the  fill value of a sds
        function fillvalue = get_sds_fillvalue(filename, sds_name)
            sd_id =  SDS_INFO.open_hdf_file(filename); 
            sds_id = SDS_INFO.get_id_from_sdsname (sd_id, sds_name);
            [fillvalue, status] = hdfsd('getfillvalue',sds_id);
            SDS_INFO.test_status('hdfsd[getfillvalue]', status);
            disp(fillvalue);
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
        end
        %------------------------------------
                
        % Set start, stride and edge
        function sds_param = set_start_stride_edge(number_of_dim, size_of_dim)
            sds_param.start = zeros(1,  number_of_dim);
            sds_param.stride = [];
            sds_param.edges = size_of_dim;
        end
        %------------------------------------
        
         % Get binary data of a sds 
        function data = get_sds_binary_data(filename, sds_name, number_of_dim, size_of_dim )
            sd_id = SDS_INFO.open_hdf_file( filename) ;
            sds_id = SDS_INFO.get_id_from_sdsname(sd_id, sds_name);
            if nargin == 2
                data = hdfread (filename, sds_name) ;
%                  disp([data(8,955)]) ;
            elseif nargin == 4
                 sds_param = SDS_INFO.set_start_stride_edge(number_of_dim, size_of_dim);
                [data, status] = hdfsd('readdata', sds_id, sds_param.start, sds_param.stride, sds_param.edges) ;
                SDS_INFO.test_status('hdfsd[readdata]', status);
             else
                   error ('Invalid inputs argument');
            end
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
        end
        %------------------------------------

         % Get scaled data of a sds
        function scaled_data = get_sds_scaled_data(filename, sds_name)
            sd_id = SDS_INFO.open_hdf_file( filename) ;
            sds_id = SDS_INFO.get_id_from_sdsname(sd_id, sds_name);
            data = hdfread (filename, sds_name) ;
            [scale_factor, scale_factor_err, offset, offset_err, format, status]...
                = hdfsd('getcal',sds_id);
            SDS_INFO.test_status('hdfsd[getcal]', status);
            [sds_name, sds_num_dim, sds_dim, sds_data_type, sds_num_attr] = hdfsd('getinfo', sds_id);
            [fillvalue, status] = hdfsd('getfillvalue',sds_id);         
            SDS_INFO.test_status('hdfsd[getfillvalue]', status);
            scaled_data = scale_factor*(data - offset );
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
%              disp([scaled_data(1,3)]) ;
        end
        %------------------------------------
        
        % Get a string describing a sds
        function info = get_sds_info(filename, sds_name)
            sd_id = SDS_INFO.open_hdf_file( filename) ;
            sds_id = SDS_INFO.get_id_from_sdsname(sd_id, sds_name);
            [sds_name, sds_num_dim, sds_dim, sds_data_type, sds_num_attr] = hdfsd('getinfo', sds_id) ;
            sds_info =  SDS_INFO (filename, sds_name, sds_num_dim, sds_dim, sds_data_type, sds_num_attr);
            info = sds_info.get_obj_caracteristics();          
            SDS_INFO.close_sds_access (sds_id);
            SDS_INFO.close_hdf_file (sd_id);
        end
        %------------------------------------
    end  % end static methods
    
    
    methods 
        % Get a string describing a SDS_INFO intance
        function info = get_obj_caracteristics ( self )
          info = sprintf('HDF file: %s\nDataset name: %s\nNumber of dimensions: %d\nSize of dimensions: %s\nData type: %s\nNumber of attributes: %d',...
                                self.file_name, self.sds_name, self.number_of_dim,num2str(self.size_of_dim),  self.sds_datatype, self.number_of_attributes );
        end
        %------------------------------------
        
         % Set attributes of a SDS_INFO instance
        function set_obj_caracteristics ( file_name, sds_name, number_of_dim, size_of_dim, sds_datatype, number_of_attributes)
            self.file_name = file_name  ;
            self.sds_name =  sds_name;
            self.number_of_dim =number_of_dim ;
            self.size_of_dim = size_of_dim;
            self.sds_datatype = sds_datatype;
            self.number_of_attributes = number_of_attributes;
        end
        %------------------------------------
                
        % Get a string describing the hdf file
        function info = get_hdf_info (self)
            hdf_file = self.file_name ;
            hdf_id = self.open_hdf_file( hdf_file) ;
            [num_datasets, num_global_attr, status] = hdfsd('fileinfo',hdf_id) ;
            self.test_status('hdfsd[fileinfo]', status);
            info = sprintf('HDF file name:  %s\nHDF file identifier: %d\nNumber of datasets: %d\nNumber of global attributes: %d',...
                                hdf_file, hdf_id, num_datasets, num_global_attr);
                            
            self.close_hdf_file (hdf_id);
        end        
        %------------------------------------
            
       %  Get caracteristics for all attributes of a sds
        function attr = get_sds_attr_by_obj (obj)
            sd_id = obj.open_hdf_file( obj.file_name) ;
            sds_id = obj.get_id_from_sdsname (sd_id, obj.sds_name);
            attr = struct([]);
            obj.create_attr( sds_id, obj.number_of_attributes, attr, '');           
            obj.close_sds_access (sds_id);
            obj.close_hdf_file (sd_id);
        end
        
        % Get attributes of the hdf file
        function attr = get_hdf_attr (obj)
            sd_id = obj.open_hdf_file( obj.file_name) ;        
            [num_datasets, num_global_attr, status] = hdfsd('fileinfo',sd_id);
            obj.test_status('hdfsd[fileinfo]', status);
            attr = struct([]);
            obj.create_attr(sd_id, num_global_attr, attr, '');
            obj.close_hdf_file (sd_id);
        end
        %------------------------------------       
    end
end

      

